package com.example.orderservice.service;

import com.example.orderservice.dto.OrderInfoDto;
import com.example.orderservice.dto.OrderItemInfoDto;
import com.example.orderservice.dto.request.OrderCreateRequest;
import com.example.orderservice.dto.request.OrderItemRequestDto;
import com.example.orderservice.dto.response.*;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.entity.OrderStatus;
import com.example.orderservice.exception.CustomException;
import com.example.orderservice.exception.ErrorCode;
import com.example.orderservice.kafka.event.OrdersReadyForPaymentEvent;
import com.example.orderservice.repository.OrderItemRepository;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.orderservice.entity.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    private final ApplicationEventPublisher applicationEventPublisher;


    /**
     * 주문 상태 변경
     * @param restaurantId
     * @param tableId
     * @return
     */
    @Transactional
    public OrderStatusChangeResponse changeOrderStatus(Long restaurantId, Long tableId) {
        // 1) 식당, 테이블 존재 여부

        // 2) IN_PROGRESS 주문 목록 조회
        List<Order> pendingOrders = orderRepository.findAllByRestaurantAndTableAndStatus(
                restaurantId, tableId, OrderStatus.IN_PROGRESS);

        if (pendingOrders.isEmpty()) {
            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
        }

        // 3) 주문 상태값 IN_PROGRESS -> COMPLETED 로 변경
        List<Long> updatedIds = new ArrayList<>();
        for (Order o : pendingOrders) {
            o.changeOrderStatus(OrderStatus.COMPLETED);
            updatedIds.add(o.getId());
        }

        // 총 금액 계산
        long totalAmount = pendingOrders.stream()
                .mapToLong(Order::getTotalAmount)
                .sum();

        // 이벤트 생성 & 발행
        applicationEventPublisher.publishEvent(OrdersReadyForPaymentEvent.of(updatedIds));

        // 4) 응답 DTO
        return OrderStatusChangeResponse.of(updatedIds.size(), updatedIds, totalAmount);
    }

    @Transactional
    public void changeOrderStatusToInProgress(List<Long> orderIds) {

        List<Order> orders = orderRepository.findAllById(orderIds);

        for (Order o : orders) {
            o.changeOrderStatus(OrderStatus.IN_PROGRESS);
        }
    }

    /**
     * 주문 생성
     * @param restaurantId
     * @param tableId
     * @param orderCreateRequest
     * @return
     */
    @Transactional
    public OrderCreateResponse createOrder(Long restaurantId, Long tableId, OrderCreateRequest orderCreateRequest) {

        // 1) 오늘 날짜 기준, 식당별 최대 orderCode 조회 → +1
        LocalDate today = LocalDate.now();
        int newOrderCode = orderRepository.findMaxOrderCodeToday(restaurantId, today) + 1;  // 매일 1부터 시작

        // 2) Order 엔티티 생성
        Order order = orderCreateRequest.toEntity(OrderStatus.IN_PROGRESS, restaurantId, tableId, newOrderCode);
        Order savedOrder = orderRepository.save(order);

        // 3) OrderItem 엔티티 생성
        List<OrderItem> savedItems = new ArrayList<>();
        for (OrderItemRequestDto orderItemRequestDto : orderCreateRequest.getOrderItemRequestDtos()) {

            // (A) DB에서 Menu 엔티티 조회
//            Menu menu = menuRepository.findById(itemReq.getMenuId())
//                    .orElseThrow(() -> new RuntimeException("해당 메뉴를 찾을 수 없습니다. menuId=" + itemReq.getMenuId()));

            // (B) 실제 주문 시점 가격, 메뉴명
            //     itemReq.getItemPrice()는 참고용 → DB 메뉴 가격을 우선시하거나,
            //     or if you allow dynamic pricing, then compare them, etc.
//            BigDecimal finalPrice = menu.getPrice(); // DB의 price
            // or if you want to override with itemReq, do finalPrice = itemReq.getItemPrice();

            OrderItem orderItem = orderItemRequestDto.toEntity(savedOrder);

            OrderItem savedItem = orderItemRepository.save(orderItem);
            savedItems.add(savedItem);
        }

        List<OrderItemResponseDto> orderItemResponseDtos = savedItems.stream()
                .map(OrderItemResponseDto::of)
                .collect(Collectors.toList());  // List로 수집

        return OrderCreateResponse.of(order, orderItemResponseDtos);
    }

    /**
     * 식당 주문 내역 조회
     * @param restaurantId
     * @param orderStatus
     * @param orderCode
     * @return
     */
    public RestaurantOrderHistoryResponse getRestaurantOrderHistory(Long restaurantId,
                                                                    OrderStatus orderStatus, String orderCode) {

        // 2) 주문 목록 조회 (식당 전체 + 상태 + 정렬)
        List<Order> orders;
        if ("desc".equalsIgnoreCase(orderCode)) {
            orders = orderRepository.findOrderByRestaurantStatusDesc(restaurantId, orderStatus);
        } else {
            orders = orderRepository.findOrderByRestaurantStatusAsc(restaurantId, orderStatus);
        }

        // 3) DTO 변환
        List<OrderInfoDto> orderInfoList = new ArrayList<>();
        for (Order o : orders) {
            // (A) orderItemsRepository로 orderId에 해당하는 item 목록 조회
            List<OrderItem> itemList = orderItemRepository.findByOrdersOrderId(o.getId());

            // (B) 아이템 목록 DTO
            List<OrderItemInfoDto> itemInfos = new ArrayList<>();
            for (OrderItem oi : itemList) {
                itemInfos.add(OrderItemInfoDto.of(oi));
            }

            orderInfoList.add(OrderInfoDto.of(o, itemInfos));
        }

        return RestaurantOrderHistoryResponse.of(orderInfoList);
    }

    /**
     * 테이블 주문 내역 조회
     * @param restaurantId
     * @param tableId
     * @param orderStatus
     * @param orderCode
     * @param lang
     * @return
     */
    public TableOrderHistoryResponse getTableOrderHistory(Long restaurantId, Long tableId,
                                                          OrderStatus orderStatus,String orderCode, String lang) {
        // 1) 식당, 테이블 존재 여부
//        Restaurant restaurant = restaurantRepository.findById(restaurantId)
//                .orElseThrow(() -> new RuntimeException("해당 식당을 찾을 수 없습니다."));
//        RestaurantTables restaurantTables = restaurantTablesRepository.findById(tableId)
//                .orElseThrow(() -> new RuntimeException("해당 테이블을 찾을 수 없습니다."));

        // 2) 주문 목록 조회
        List<Order> orders;
        if ("desc".equalsIgnoreCase(orderCode)) {
            orders = orderRepository.findOrderByStatusDesc(restaurantId, tableId, orderStatus);
        } else {
            orders = orderRepository.findOrderByStatusAsc(restaurantId, tableId, orderStatus);
        }

        // 3) DTO 변환
        List<OrderInfoDto> orderInfoList = new ArrayList<>();
        for (Order o : orders) {
            // (A) orderItemsRepository로 orderId에 해당하는 item 목록 조회
            List<OrderItem> itemList = orderItemRepository.findByOrdersOrderId(o.getId());

            // (B) 아이템 목록 DTO
            List<OrderItemInfoDto> itemInfos = new ArrayList<>();
            for (OrderItem oi : itemList) {
                itemInfos.add(OrderItemInfoDto.of(oi));
            }

            orderInfoList.add(OrderInfoDto.of(o, itemInfos));
        }

        return TableOrderHistoryResponse.of(orderInfoList);

//        // 3) DTO 변환
//        List<TableOrderHistoryResponse.OrderInfo> orderInfoList = new ArrayList<>();
//        for (Order o : ordersList) {
//
//            // [CHANGED] orderItemsRepository로 orderId에 해당하는 item 목록 조회
//            List<OrderItem> itemList = orderItemRepository.findByOrdersOrderId(o.getId());
//
//            // 아이템 목록 DTO
//            List<TableOrderHistoryResponse.OrderItemInfo> itemInfos = new ArrayList<>();
//            for (OrderItem oi : itemList) {
//                // (A) Menu 엔티티 참조
////                Menu menu = oi.get();
//
//                // (B) lang 에 따라 다른 이름을 선택
//                String translatedMenuName = resolveMenuName(menu, lang);
//
//
//                itemInfos.add(
//                        TableOrderHistoryResponse.OrderItemInfo.builder()
//                                .menuId(oi.getMenu() != null ? oi.getMenu().getMenuId() : null)
//                                .menuName(translatedMenuName) // <-- 수정된 부분
//                                .quantity(oi.getQuantity())
//                                .itemPrice(oi.getItemPrice())
//                                .build()
//                );
//            }
//
//            TableOrderHistoryResponse.OrderInfo orderInfo = TableOrderHistoryResponse.OrderInfo.builder()
//                    .orderId(o.getOrderId())
//                    .orderCode(o.getOrderCode())
//                    .createdAt(o.getCreatedAt())
//                    .totalAmount(o.getTotalAmount())
//                    .orderStatus(o.getOrderStatus())
//                    .items(itemInfos)
//                    .build();
//
//            orderInfoList.add(orderInfo);
//        }
//
//        return TableOrderHistoryResponse.builder()
//                .status(200)
//                .success(true)
//                .data(orderInfoList)
//                .message("주문 이력을 조회했습니다.")
//                .build();
    }
//    /**
//     * lang에 따라 menu 엔티티의 적절한 이름(menuNameEn, menuNameZh, menuNameJp 등)을 반환
//     * 만약 해당 언어 컬럼이 null이거나 비어있다면 기본값(한글 menuName) 사용
//     */
//    private String resolveMenuName(Menu menu, String lang) {
//        if (menu == null) {
//            return "(알 수 없는 메뉴)";
//        }
//        switch (lang) {
//            case "en":
//                return (menu.getMenuNameEn() != null && !menu.getMenuNameEn().isEmpty())
//                        ? menu.getMenuNameEn() : menu.getMenuName(); // fallback
//            case "zh":
//                return (menu.getMenuNameZh() != null && !menu.getMenuNameZh().isEmpty())
//                        ? menu.getMenuNameZh() : menu.getMenuName();
//            case "jp":
//                return (menu.getMenuNameJp() != null && !menu.getMenuNameJp().isEmpty())
//                        ? menu.getMenuNameJp() : menu.getMenuName();
//            default:
//                // kr or anything else
//                return menu.getMenuName();
//        }
//    }
}
