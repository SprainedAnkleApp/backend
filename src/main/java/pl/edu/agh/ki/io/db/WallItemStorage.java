package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.api.models.WallItemResponse;
import pl.edu.agh.ki.io.models.wallElements.WallItem;
import pl.edu.agh.ki.io.models.wallElements.WallItemPage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WallItemStorage {
    private final WallItemRepository wallItemRepository;
    private final ReactionsRepository reactionsRepository;
    public WallItemStorage(WallItemRepository wallItemRepository, ReactionsRepository reactionsRepository){
        this.wallItemRepository = wallItemRepository;
        this.reactionsRepository = reactionsRepository;
    }

    public Page<WallItemResponse> findAll(WallItemPage wallItemPage) {
        Sort sort = Sort.by(wallItemPage.getSortDirection(), wallItemPage.getSortBy());
        Pageable pageable = PageRequest.of(wallItemPage.getPageNumber(),
                wallItemPage.getPageSize(), sort);
        Page<WallItem> wallItems = this.wallItemRepository.findAll(pageable);

        return new PageImpl<>(wallItems.stream()
                .map(wallItem -> {
                    try {
                        return WallItemResponse.fromWallItemAndReactions(wallItem, this.reactionsRepository.findByIdWallElementID(wallItem.getId()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList()),
                pageable, wallItems.getTotalElements());
    }

    public Optional<WallItem> getWallItemById(Long wallItemId) {
        return this.wallItemRepository.findById(wallItemId);
    }

}
