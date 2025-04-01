module.exports = {
    apps: [
        {
            name: "main-https-server",
            script: "http-server",
            args: "-p 8000 --cors -a 0.0.0.0 --ssl --cert localhost2.pem --key localhost2_key.pem"
        },
        {
            name: "proxy-server",
            script: "proxy-server/server.js"
        },
        {
            name: "auth-status-server",
            script: "auth-status-server/server.js"
        },
        {
            name: "qr-html-server",
            script: "http-server",
            args: "-p 8080 --cors"
        }
    ]
};
