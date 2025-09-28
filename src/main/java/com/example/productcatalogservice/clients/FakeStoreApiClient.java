package com.example.productcatalogservice.clients;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class FakeStoreApiClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductDto getProductById(Long id) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity(HttpMethod.GET, "https://fakestoreapi.com/products/{id}",null,
                        FakeStoreProductDto.class,id);
        if(validateResponse(fakeStoreProductDtoResponseEntity)) {
            return fakeStoreProductDtoResponseEntity.getBody();
        }
        return null;
    }

    public List<FakeStoreProductDto> getAllProducts(){
        ResponseEntity<FakeStoreProductDto[]> fakeStoreProductDtoListResponseEntity =
                requestForEntity(HttpMethod.GET,"https://fakestoreapi.com/products",null,
                                FakeStoreProductDto[].class);

        if(validateResponse(fakeStoreProductDtoListResponseEntity)) {
            FakeStoreProductDto[] fakeStoreProductDtoList = fakeStoreProductDtoListResponseEntity.getBody();
            return List.of(fakeStoreProductDtoList);
        }
        return List.of();
    }

    public FakeStoreProductDto createProduct(FakeStoreProductDto fakeStoreProductDto) {
        ResponseEntity<FakeStoreProductDto> response = requestForEntity(
                HttpMethod.POST,"https://fakestoreapi.com/products",fakeStoreProductDto,
                FakeStoreProductDto.class
        );
        if(validateResponse(response)) {
            return response.getBody();
        }
        return null;
    }

    public FakeStoreProductDto replaceProduct(FakeStoreProductDto fakeStoreProductDto, Long id) {
        ResponseEntity<FakeStoreProductDto> response = requestForEntity(
                HttpMethod.PUT,"https://fakestoreapi.com/products/{id}",fakeStoreProductDto,
                FakeStoreProductDto.class,id
        );
        if(validateResponse(response)) {
            return response.getBody();
        }
        return null;
    }

    private <T> Boolean validateResponse(ResponseEntity<T> fakeStoreProductDtoResponseEntity) {
        HttpStatusCode statusCode = fakeStoreProductDtoResponseEntity.getStatusCode();
        return (statusCode.equals(HttpStatusCode.valueOf(200)) ||
                statusCode.equals(HttpStatusCode.valueOf(201)))
                && fakeStoreProductDtoResponseEntity.hasBody();
    }

    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod , String url, @Nullable Object request,
                                                   Class<T> responseType, Object... uriVariables)
            throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}
