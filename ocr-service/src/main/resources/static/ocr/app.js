const video = document.getElementById("video");
const canvas = document.getElementById("canvas");
const captureButton = document.getElementById("captureButton");
const retakeButton = document.getElementById("retakeButton");
const nextCaptureButton = document.getElementById("nextCaptureButton");
const completeButton = document.getElementById("completeButton");
const brightnessButton = document.getElementById("brightnessButton");
const resultDiv = document.getElementById("result");
const loadingDiv = document.getElementById("loading");
const capturedImage = document.getElementById("capturedImage");
const ctx = canvas.getContext("2d");

const BASE_URL = "https://team08.kro.kr/ocr";
const PROXY_URL = "https://team08.kro.kr/api/ocr";
const INCREMENT_URL = `https://team08.kro.kr/api/auth/increment`;

let capturedImages = [];
let isScreenDark = false;

document.addEventListener("DOMContentLoaded", () => {
    initCamera();
});

async function initCamera() {
    try {
        if (video.srcObject) {
            video.srcObject.getTracks().forEach((track) => track.stop());
            video.srcObject = null;
        }

        const constraints = {
            video: {
                width: { ideal: 1280 },
                height: { ideal: 720 },
                facingMode: "environment",
                aspectRatio: { ideal: 4 / 3 },
            },
            audio: false,
        };

        const stream = await navigator.mediaDevices.getUserMedia(constraints);
        video.srcObject = stream;

        video.onloadedmetadata = () => {
            video.play().catch((err) => console.error("비디오 재생 실패:", err));
        };

        video.style.display = "block";
        capturedImage.style.display = "none";
    } catch (err) {
        console.error("카메라 접근 오류:", err);
        alert("카메라 접근에 실패했습니다. 권한을 허용해주세요.");
    }
}

function extractGuideBoxRegion(sourceCanvas) {
    const guideBox = document.querySelector(".guide-box");
    const guideBoxRect = guideBox.getBoundingClientRect();
    const containerRect = document.querySelector(".camera-container").getBoundingClientRect();

    const relativeX = (guideBoxRect.left - containerRect.left) / containerRect.width;
    const relativeY = (guideBoxRect.top - containerRect.top) / containerRect.height;
    const relativeWidth = guideBoxRect.width / containerRect.width;
    const relativeHeight = guideBoxRect.height / containerRect.height;

    const x = relativeX * sourceCanvas.width;
    const y = relativeY * sourceCanvas.height;
    const width = relativeWidth * sourceCanvas.width;
    const height = relativeHeight * sourceCanvas.height;

    const padding = 20;
    const extractCanvas = document.createElement("canvas");
    extractCanvas.width = width + padding * 2;
    extractCanvas.height = height + padding * 2;
    const extractCtx = extractCanvas.getContext("2d");

    extractCtx.drawImage(
        sourceCanvas,
        Math.max(0, x - padding),
        Math.max(0, y - padding),
        width + padding * 2,
        height + padding * 2,
        0,
        0,
        extractCanvas.width,
        extractCanvas.height
    );

    return extractCanvas;
}

function maskRRN(rrn) {
    if (!rrn || rrn === "정보 없음") return rrn;
    if (rrn.includes("-")) {
        const parts = rrn.split("-");
        return `${parts[0]}-${parts[1]?.charAt(0)}******`;
    }
    if (rrn.length === 13) {
        return `${rrn.substring(0, 6)}-${rrn.charAt(6)}******`;
    }
    return rrn;
}

async function sendToClova(imageBase64) {
    const base64Data = imageBase64.replace(/^data:image\/\w+;base64,/, "");

    try {
        const response = await fetch(PROXY_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                version: "V2",
                requestId: "request-" + new Date().getTime(),
                timestamp: new Date().getTime(),
                images: [{ format: "jpg", name: "id-document", data: base64Data }],
            }),
        });

        if (!response.ok) {
            throw new Error(`API 오류: ${response.status}`);
        }

        return await response.json();
    } catch (error) {
        console.error("CLOVA OCR API 호출 오류:", error);
        throw error;
    }
}

function extractInformationFromResponse(response) {
    try {
        const result = response?.images?.[0]?.idCard?.result;
        if (!result) return null;

        const idInfo = {};

        if (result.dl) {
            const dl = result.dl;
            idInfo.type = "Driver's License";
            idInfo.name = dl.name?.[0]?.text || "정보 없음";
            idInfo.rrn = dl.personalNum?.[0]?.text || "정보 없음";
            idInfo.address = dl.address?.[0]?.text || "정보 없음";
            idInfo.issueDate = dl.issueDate?.[0]?.text || "정보 없음";
            idInfo.licenseNum = dl.num?.[0]?.text || "정보 없음";
            idInfo.renewStartDate = dl.renewStartDate?.[0]?.text || "정보 없음";
            idInfo.renewEndDate = dl.renewEndDate?.[0]?.text || "정보 없음";
            idInfo.condition = dl.condition?.[0]?.text || "정보 없음";
        } else if (result.ic) {
            const ic = result.ic;
            idInfo.type = "ID Card";
            idInfo.name = ic.name?.[0]?.text || "정보 없음";
            idInfo.rrn = ic.personalNum?.[0]?.text || "정보 없음";
            idInfo.address = ic.address?.[0]?.text || "정보 없음";
            idInfo.issueDate = ic.issueDate?.[0]?.text || "정보 없음";
        } else {
            return null;
        }

        idInfo.rrn = maskRRN(idInfo.rrn);
        return idInfo;
    } catch (error) {
        console.error("OCR 파싱 오류:", error);
        return null;
    }
}

function resetOCRState() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    capturedImage.src = "";
    resultDiv.innerHTML = "<h2>인식 결과</h2>";
    loadingDiv.style.display = "none";
}

async function captureAndProcess() {
    try {
        resetOCRState();

        canvas.width = video.videoWidth;
        canvas.height = video.videoHeight;
        ctx.drawImage(video, 0, 0);

        if (video.srcObject) {
            video.srcObject.getTracks().forEach((track) => track.stop());
        }

        capturedImage.src = canvas.toDataURL("image/jpeg");
        video.style.display = "none";
        capturedImage.style.display = "block";

        captureButton.style.display = "none";
        retakeButton.style.display = "inline-block";
        nextCaptureButton.style.display = "inline-block";
        completeButton.style.display = "inline-block";

        loadingDiv.style.display = "block";
        resultDiv.innerHTML = "<h2>인식 결과</h2>";

        const guideBoxCanvas = extractGuideBoxRegion(canvas);
        const imageBase64 = guideBoxCanvas.toDataURL("image/jpeg");
        await new Promise((resolve) => setTimeout(resolve, 100));

        const ocrResult = await sendToClova(imageBase64);
        const idInfo = extractInformationFromResponse(ocrResult);

        if (idInfo) {
            capturedImages.push(idInfo);
            let resultHTML = "<h2>인식 결과</h2><div class='result-container'>";
            resultHTML += `<p>신분증 종류: <strong>${idInfo.type}</strong></p>`;
            resultHTML += `<p>이름: <strong>${idInfo.name}</strong></p>`;
            resultHTML += `<p>주민등록번호: <strong>${idInfo.rrn}</strong></p>`;
            resultHTML += `<p>주소: <strong>${idInfo.address}</strong></p>`;
            resultHTML += `<p>발급일자: <strong>${idInfo.issueDate}</strong></p>`;

            if (idInfo.renewStartDate) {
                resultHTML += `<p>갱신 시작일: <strong>${idInfo.renewStartDate}</strong></p>`;
            }
            if (idInfo.renewEndDate) {
                resultHTML += `<p>갱신 종료일: <strong>${idInfo.renewEndDate}</strong></p>`;
            }

            resultHTML += "</div>";
            resultDiv.innerHTML = resultHTML;
        } else {
            resultDiv.innerHTML =
                "<h2>인식 결과</h2><p class='error-message'>신분증 정보를 인식하지 못했습니다.</p>";
        }
    } catch (err) {
        console.error("OCR 처리 오류:", err);
        resultDiv.innerHTML =
            "<h2>인식 결과</h2><p class='error-message'>오류 발생. 다시 시도해주세요.</p>";
    } finally {
        loadingDiv.style.display = "none";
    }
}

async function nextCapture() {
    resetOCRState();
    if (video.srcObject) {
        video.srcObject.getTracks().forEach((track) => track.stop());
        video.srcObject = null;
    }

    captureButton.style.display = "inline-block";
    retakeButton.style.display = "none";
    nextCaptureButton.style.display = "none";
    completeButton.style.display = "none";

    await initCamera();
    video.style.display = "block";
    capturedImage.style.display = "none";
}

async function retake() {
    resetOCRState();
    if (video.srcObject) {
        video.srcObject.getTracks().forEach((track) => track.stop());
        video.srcObject = null;
    }

    if (capturedImages.length > 0) {
        capturedImages.pop();
    }

    captureButton.style.display = "inline-block";
    retakeButton.style.display = "none";
    nextCaptureButton.style.display = "none";
    completeButton.style.display = "none";

    await initCamera();
    video.style.display = "block";
    capturedImage.style.display = "none";
}

async function complete() {
    try {
        await fetch(INCREMENT_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
        });

        alert(`신분증 정보가 성공적으로 처리되었습니다.`);
        console.log("모든 캡처된 정보:", capturedImages);
    } catch (error) {
        console.error("인증 완료 오류:", error);
        alert("인증 완료 처리 중 오류가 발생했습니다.");
    }
}

function toggleBrightness() {
    isScreenDark = !isScreenDark;
    video.style.filter = isScreenDark ? "brightness(0.5)" : "brightness(1)";
    brightnessButton.textContent = isScreenDark ? "화면 밝게" : "화면 어둡게";
}

captureButton.addEventListener("click", captureAndProcess);
retakeButton.addEventListener("click", retake);
nextCaptureButton.addEventListener("click", nextCapture);
completeButton.addEventListener("click", complete);
brightnessButton.addEventListener("click", toggleBrightness);
