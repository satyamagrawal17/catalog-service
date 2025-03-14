package com.example.catalog_service.service.grpcService;

import com.example.catalog_service.dto.ItemRequestDto;
import com.example.catalog_service.grpc.*;
import com.example.catalog_service.model.Item;
import com.example.catalog_service.service.ItemService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrpcItemService extends ItemServiceGrpc.ItemServiceImplBase {
    private final ItemService itemService;
    @Override
    public void createItem(CreateItemRequest request, StreamObserver<MenuItem> responseObserver) {
        try {
            ItemRequestDto itemRequestDto = new ItemRequestDto();
            itemRequestDto.setName(request.getName());
            itemRequestDto.setPrice(request.getPrice());
            itemRequestDto.setStock(request.getStock());
            Long ownerId = 1L;
            Item item = itemService.create(itemRequestDto, request.getRestaurantId(), ownerId);
            MenuItem response = convertToGrpcItem(item);
            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }
        catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getItemsByRestaurantId(GetItemsByRestaurantIdRequest request, StreamObserver<GetItemsByRestaurantIdResponse> responseObserver) {
        try {
            GetItemsByRestaurantIdResponse.Builder responseBuilder = GetItemsByRestaurantIdResponse.newBuilder();
            itemService.getAllItems(request.getRestaurantId(), 1L).forEach(item -> {
                responseBuilder.addMenuItems(convertToGrpcItem(item));
            });
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getItemById(GetItemByIdRequest request, StreamObserver<MenuItem> responseObserver) {
        try {
            Item item = itemService.fetchById(request.getMenuItemId(), 1L);
            responseObserver.onNext(convertToGrpcItem(item));
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    private MenuItem convertToGrpcItem(Item item) {
        MenuItem.Builder menuItemBuilder = MenuItem.newBuilder();
        menuItemBuilder.setId(item.getId());
        menuItemBuilder.setName(item.getName());
        menuItemBuilder.setPrice(item.getPrice());
        menuItemBuilder.setStock(item.getStock());
        return menuItemBuilder.build();
    }
}
