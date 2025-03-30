package com.example.restaurantservice.service.menu;

import com.example.restaurantservice.dto.request.MenuCreateRequest;
import com.example.restaurantservice.dto.request.MenuUpdateRequest;
import com.example.restaurantservice.dto.response.MenuCreateResponse;
import com.example.restaurantservice.dto.response.MenuDeleteResponse;
import com.example.restaurantservice.dto.response.MenuUpdateResponse;
import com.example.restaurantservice.entity.menu.Menu;
import com.example.restaurantservice.entity.menu.MenuCategory;
import com.example.restaurantservice.entity.restaurant.Restaurant;
import com.example.restaurantservice.exception.CustomException;
import com.example.restaurantservice.exception.ErrorCode;
import com.example.restaurantservice.repository.menu.MenuCategoryRepository;
import com.example.restaurantservice.repository.menu.MenuRepository;
import com.example.restaurantservice.repository.restaurant.RestaurantRepository;
import com.example.restaurantservice.service.translation.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;
    private final RestaurantRepository restaurantRepository;
    private final TranslationService translationService;

    /**
     * 메뉴 생성
     * @param restaurantId
     * @param menuCreateRequest
     * @return
     */
    @Transactional
    public MenuCreateResponse createMenu(Long restaurantId, MenuCreateRequest menuCreateRequest) {

        // 1) restaurantId 유효성 검증
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        // 2) 카테고리 처리
        MenuCategory category;
        // 카테고리 id가 존재할 경우 -> 좀 이상함.. 카테고리명을 여기서 바꾸면, 상ㅇ위 카테고리 이름이 다 바뀌는데..?
        if (menuCreateRequest.getMenuCategoryId() != null) {
            category = menuCategoryRepository.findById(menuCreateRequest.getMenuCategoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

            // 카테고리 한글명이 새로 넘어왔으면 업데이트
            if (menuCreateRequest.getMenuCategoryName() != null && !menuCreateRequest.getMenuCategoryName().isEmpty()) {
                category.updateMenuCategoryName(menuCreateRequest.getMenuCategoryName());

                category.updateMenuCategoryNameEn(translationService.translateToEnglish(menuCreateRequest.getMenuCategoryName()));
                category.updateMenuCategoryNameZh(translationService.translateToChinese(menuCreateRequest.getMenuCategoryName()));
                category.updateMenuCategoryNameJp(translationService.translateToJapanese(menuCreateRequest.getMenuCategoryName()));
            }
        } else {
            // 새 카테고리 등록
            if (menuCreateRequest.getMenuCategoryName() == null ||
                    menuCreateRequest.getMenuCategoryName().isEmpty()) {
                throw new CustomException(ErrorCode.CATEGORY_NOT_FOUND);
            }

            MenuCategory newCategory = MenuCategory.builder()
                    .menuCategoryName(menuCreateRequest.getMenuCategoryName())
                    .menuCategoryNameEn(translationService.translateToEnglish(menuCreateRequest.getMenuCategoryName()))
                    .menuCategoryNameZh(translationService.translateToChinese(menuCreateRequest.getMenuCategoryName()))
                    .menuCategoryNameJp(translationService.translateToJapanese(menuCreateRequest.getMenuCategoryName()))
//                    .displayOrder()           // 이거 처리하기
                    .restaurant(restaurant)
                    .build();

            category = menuCategoryRepository.save(newCategory);
        }

        // 3) Menu 엔티티 생성
        Menu menu = menuCreateRequest.toEntity(restaurant, category);

        // 4) 메뉴, 설명 번역 및 저장
        updateTranslatedMenuName(menu);
        updateTranslatedMenuName(menu);

        // [CHANGED] 5) displayOrder = (카테고리 내) 최댓값 + 1
        Integer maxOrder = menuRepository.findMaxDisplayOrderByCategoryId(category.getId());
        if (maxOrder == null) {
            maxOrder = 0;
        }
        menu.setDisplayOrder(maxOrder + 1);

        // 6) save
        Menu savedMenu = menuRepository.save(menu);

        // 7) 응답
        return MenuCreateResponse.of(savedMenu.getId(), category.getId());
    }

    /**
     * 메뉴 삭제
     * @param restaurantId
     * @param menuId
     * @return
     */
    @Transactional
    public MenuDeleteResponse deleteMenu(Long restaurantId, Long menuId) {

        // 1) 메뉴 존재 여부 검증
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        // 2) 삭제
        menuRepository.delete(menu);

        // 3) 응답 DTO
        return MenuDeleteResponse.of(menuId);
    }

    @Transactional
    public MenuUpdateResponse updateMenu(Long restaurantId, Long menuId, MenuUpdateRequest menuUpdateRequest) {

        // 1) 메뉴 존재 여부 검증
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        // 2) 카테고리 수정 (optional)
        if (menuUpdateRequest.getMenuCategoryId() != null) {
            // (A) 기존 카테고리 찾기
            MenuCategory category = menuCategoryRepository.findById(menuUpdateRequest.getMenuCategoryId())
                    .orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));

            // (B) 한글 카테고리명 넘어오면 변경
            if (menuUpdateRequest.getMenuCategoryName() != null && !menuUpdateRequest.getMenuCategoryName().isEmpty()) {
                category.updateMenuCategoryName(menuUpdateRequest.getMenuCategoryName());

                category.updateMenuCategoryNameEn(translationService.translateToEnglish(menuUpdateRequest.getMenuCategoryName()));
                category.updateMenuCategoryNameZh(translationService.translateToChinese(menuUpdateRequest.getMenuCategoryName()));
                category.updateMenuCategoryNameJp(translationService.translateToJapanese(menuUpdateRequest.getMenuCategoryName()));
            }
        }

        // 3) 메뉴 수정
        // (A) 한글 메뉴명
        if (menuUpdateRequest.getMenuName() != null && !menuUpdateRequest.getMenuName().isEmpty()) {
            // 새 한글명 반영
            menu.changeMenuName(menuUpdateRequest.getMenuName());
            // 번역 (영/중/일)
            updateTranslatedMenuName(menu);
        }

        // (B) 한글 메뉴 설명
        if (menuUpdateRequest.getMenuDescription() != null) {
            // 새 한국어 메뉴설명 반영
            menu.changeMenuDescription(menuUpdateRequest.getMenuDescription());
            // 번역(영/중/일)
            updateTranslatedMenuDescription(menu);
        }

        // (C) 가격
        if (menuUpdateRequest.getPrice() != null) {
            menu.updatePrice(menuUpdateRequest.getPrice());
        }

        // (D) 이미지
        if (menuUpdateRequest.getMenuImageUrl() != null) {
            menu.updateMenuImageUrl(menuUpdateRequest.getMenuImageUrl());
        }

        // (E) displayOrder는 수정하지 않는다고 가정(필요 시 처리)

        return MenuUpdateResponse.of(menu.getId(), menu.getMenuCategory().getId(),
                menu.getMenuName(), menu.getMenuDescription(), menu.getMenuImageUrl());
    }

    private void updateTranslatedMenuName(Menu menu){

        menu.updateEnglishVersionName(translationService.translateToEnglish(menu.getMenuName()));
        menu.updateChineseVersionName(translationService.translateToChinese(menu.getMenuName()));
        menu.updateJapaneseVersionName(translationService.translateToJapanese(menu.getMenuName()));
    }

    private void updateTranslatedMenuDescription(Menu menu){

        menu.updateEnglishVersionDescription(translationService.translateToEnglish(menu.getMenuDescription()));
        menu.updateChineseVersionDescription(translationService.translateToChinese(menu.getMenuDescription()));
        menu.updateJapaneseVersionDescription(translationService.translateToJapanese(menu.getMenuDescription()));
    }

//    public OrderCreateResponse createOrder(Long restaurantId, Long tableId, OrderCreateRequest request) {
//
//        // 1) 식당, 테이블 확인
//        Restaurant restaurant = restaurantRepository.findById(restaurantId)
//                .orElseThrow(() -> new RuntimeException("해당 식당을 찾을 수 없습니다."));
//        RestaurantTables restaurantTables = restaurantTablesRepository.findById(tableId)
//                .orElseThrow(() -> new RuntimeException("해당 테이블을 찾을 수 없습니다."));
//
//        // 2) 오늘 날짜 기준, 식당별 최대 orderCode 조회 → +1
//        LocalDate today = LocalDate.now();
//        int maxCode = ordersRepository.findMaxOrderCodeToday(restaurantId, today);
//        int newOrderCode = maxCode + 1;  // 매일 1부터 시작
//
//        // 3) Orders 엔티티 생성
//        Orders order = new Orders();
//        order.setOrderStatus("PENDING");
//        order.setTotalAmount(request.getTotalAmount());
//        order.setOrderTable(request.getOrderTable());
//        order.setCreatedAt(LocalDateTime.now());
//        order.setUpdatedAt(LocalDateTime.now());
//        order.setRestaurant(restaurant);
//        order.setRestaurantTables(restaurantTables);
//
//        // set orderCode
//        order.setOrderCode(newOrderCode);
//
//        Orders savedOrder = ordersRepository.save(order);
//
//        // 4) OrderItem 엔티티 생성
//        List<OrderItems> savedItems = new ArrayList<>();
//        for (OrderCreateRequest.OrderItemRequest itemReq : request.getOrderItems()) {
//
//            // (A) DB에서 Menu 엔티티 조회
//            Menu menu = menuRepository.findById(itemReq.getMenuId())
//                    .orElseThrow(() -> new RuntimeException("해당 메뉴를 찾을 수 없습니다. menuId=" + itemReq.getMenuId()));
//
//            // (B) 실제 주문 시점 가격, 메뉴명
//            //     itemReq.getItemPrice()는 참고용 → DB 메뉴 가격을 우선시하거나,
//            //     or if you allow dynamic pricing, then compare them, etc.
//            BigDecimal finalPrice = menu.getPrice(); // DB의 price
//            // or if you want to override with itemReq, do finalPrice = itemReq.getItemPrice();
//
//            OrderItems orderItem = new OrderItems();
//            orderItem.setMenu(menu);                  // ← FK(menu_id)
//            orderItem.setMenuName(menu.getMenuName());
//            orderItem.setQuantity(itemReq.getQuantity());
//            orderItem.setItemPrice(finalPrice);
//            orderItem.setCreatedAt(LocalDateTime.now());
//            orderItem.setOrders(savedOrder);
//
//            OrderItems savedItem = orderItemsRepository.save(orderItem);
//            savedItems.add(savedItem);
//        }
//
//        // 5) 응답 DTO 구성
//        List<OrderCreateResponse.OrderItemDto> itemDtoList = new ArrayList<>();
//        for (OrderItems si : savedItems) {
//            itemDtoList.add(OrderCreateResponse.OrderItemDto.builder()
//                    .orderItemId(si.getOrderItemId())
//                    .menuId(si.getMenu().getMenuId()) // FK(menu_id)
//                    .menuName(si.getMenuName())
//                    .quantity(si.getQuantity())
//                    .itemPrice(si.getItemPrice())
//                    .build());
//        }
//
//        OrderCreateResponse.Data data = OrderCreateResponse.Data.builder()
//                .orderId(savedOrder.getOrderId())
//                .orderStatus(savedOrder.getOrderStatus())
//                .orderTable(savedOrder.getOrderTable())
//                .tableId(tableId)
//                .orderCode(savedOrder.getOrderCode()) // 추가
//                .totalAmount(savedOrder.getTotalAmount())
//                .createdAt(savedOrder.getCreatedAt())
//                .orderItems(itemDtoList)
//                .build();
//
//        return OrderCreateResponse.builder()
//                .status(201)
//                .success(true)
//                .data(data)
//                .message("주문이 성공적으로 완료되었습니다.")
//                .build();
//    }
}