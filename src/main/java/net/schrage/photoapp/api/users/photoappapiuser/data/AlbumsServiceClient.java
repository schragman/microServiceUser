package net.schrage.photoapp.api.users.photoappapiuser.data;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import net.schrage.photoapp.api.users.photoappapiuser.ui.model.AlbumResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name="albums-ws")
public interface AlbumsServiceClient {

  @GetMapping("users/{id}/albums")
  @CircuitBreaker(name="albums-ws", fallbackMethod="getAlbumsFallback")
  List<AlbumResponseModel> getAlbums(@PathVariable String id);

  default List<AlbumResponseModel> getAlbumsFallback(String id, Throwable exception) {
    System.out.println("Param = " + id);
    System.out.println("Exception took place " + exception.getMessage());
    return new ArrayList<>();
  }
}





