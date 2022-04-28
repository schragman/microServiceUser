package net.schrage.photoapp.api.users.photoappapiuser.data;

import net.schrage.photoapp.api.users.photoappapiuser.ui.model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="albums-ws")
public interface AlbumsServiceClient {

  @GetMapping("users/{id}/albums")     //hier könnte albumss stehen, um Exceptions zu testen
  public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}
