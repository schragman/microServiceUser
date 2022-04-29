package net.schrage.photoapp.api.users.photoappapiuser.data;

import feign.FeignException;
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

//@FeignClient(name="albums-ws", fallback = AlbumsFallback.class)
@FeignClient(name="albums-ws", fallbackFactory = AlbumsFallbackFactory.class)
public interface AlbumsServiceClient {

  @GetMapping("users/{id}/albums")     //hier könnte albumss stehen, um Exceptions zu testen
  public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}

@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumsServiceClient> {
  @Override
  public AlbumsServiceClient create(Throwable cause) {
    return new AlbumsServiceClientFallback(cause);
  }
}

class AlbumsServiceClientFallback implements AlbumsServiceClient {

  private Logger logger = LoggerFactory.getLogger(this.getClass());
  private final Throwable cause;

  public AlbumsServiceClientFallback(Throwable cause) {
    this.cause = cause;
  }

  @Override
  public List<AlbumResponseModel> getAlbums(String id) {
    if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
      logger.error("404 error took place when getAlbums was called with userId: "
          + id + ". Error message: "
          + cause.getLocalizedMessage());
    } else {
      logger.error("Other error took place: " + cause.getLocalizedMessage());
    }
    return new ArrayList<>();
  }
}

/*@Component
class AlbumsFallback implements AlbumsServiceClient {
  @Override
  public List<AlbumResponseModel> getAlbums(String id) {
    return new ArrayList<>();
  }
}*/
