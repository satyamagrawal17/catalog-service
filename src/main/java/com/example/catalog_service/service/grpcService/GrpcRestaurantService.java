package com.example.catalog_service.service.grpcService;

import com.example.catalog_service.dto.AddressDto;
import com.example.catalog_service.dto.RestaurantRequestDto;
import com.example.catalog_service.grpc.*;
import com.example.catalog_service.model.Item;
import com.example.catalog_service.service.RestaurantService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GrpcRestaurantService extends RestaurantServiceGrpc.RestaurantServiceImplBase {
    private final RestaurantService restaurantService;
    @Override
    public void createRestaurant(CreateRestaurantRequest request, StreamObserver<Restaurant> responseObserver) {
        try {
            RestaurantRequestDto restaurantRequestDto = new RestaurantRequestDto();
            restaurantRequestDto.setAddress(new AddressDto());
            restaurantRequestDto.setName(request.getName());
            restaurantRequestDto.getAddress().setStreet(request.getAddress().getStreet());
            restaurantRequestDto.getAddress().setCity(request.getAddress().getCity());
            restaurantRequestDto.getAddress().setState(request.getAddress().getState());
            restaurantRequestDto.getAddress().setPostalCode(request.getAddress().getPostalCode());
            restaurantRequestDto.getAddress().setLongitude(request.getAddress().getLongitude());
            restaurantRequestDto.getAddress().setLatitude(request.getAddress().getLatitude());
            Long ownerId = 1L;

            com.example.catalog_service.model.Restaurant restaurant = restaurantService.create(restaurantRequestDto, ownerId);
            Restaurant response = convertToGrpcRestaurant(restaurant);
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getRestaurants(com.google.protobuf.Empty request, StreamObserver<GetRestaurantsResponse> responseObserver) {
        try {
            GetRestaurantsResponse.Builder responseBuilder = GetRestaurantsResponse.newBuilder();
            restaurantService.fetchAll(1L).forEach(restaurant -> {
                responseBuilder.addRestaurants(convertToGrpcRestaurant(restaurant));
            });
            responseObserver.onNext(responseBuilder.build());
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public void getRestaurantById(GetRestaurantByIdRequest request, StreamObserver<Restaurant> responseObserver) {
        try {
            com.example.catalog_service.model.Restaurant restaurant = restaurantService.fetchById(request.getId(), 1L);
            responseObserver.onNext(convertToGrpcRestaurant(restaurant));
            responseObserver.onCompleted();
        }
        catch (Exception e) {
            responseObserver.onError(io.grpc.Status.INTERNAL.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    private Restaurant convertToGrpcRestaurant(com.example.catalog_service.model.Restaurant restaurant) {
        return Restaurant.newBuilder()
                .setId(restaurant.getId())
                .setName(restaurant.getName())
                .setAddress(com.example.catalog_service.grpc.Address.newBuilder()
                        .setStreet(restaurant.getAddress().getStreet())
                        .setCity(restaurant.getAddress().getCity())
                        .setState(restaurant.getAddress().getState())
                        .setPostalCode(restaurant.getAddress().getPostalCode())
                        .setLongitude(restaurant.getAddress().getLongitude())
                        .setLatitude(restaurant.getAddress().getLatitude())
                        .build())
                .addAllMenuItems(restaurant.getItems().stream()
                        .map(this::convertToGrpcMenuItem)
                        .collect(Collectors.toList()))
                .build();
    }

    private MenuItem convertToGrpcMenuItem(Item item) {
        return MenuItem.newBuilder()
                .setId(item.getId())
                .setRestaurantId(item.getRestaurant().getId())
                .setName(item.getName())
                .setPrice(item.getPrice())
                .setStock(item.getStock())
                .build();
    }
}
