package pl.edu.agh.ki.io.db;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.api.models.WallItemResponse;
import pl.edu.agh.ki.io.models.User;
import pl.edu.agh.ki.io.models.wallElements.WallItem;
import pl.edu.agh.ki.io.models.wallElements.WallItemPage;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WallItemStorage {
    private final WallItemRepository wallItemRepository;
    private final ReactionsRepository reactionsRepository;
    private final FriendshipRepository friendshipRepository;

    public Page<WallItemResponse> findAllUserWallItems(User user, WallItemPage wallItemPage) {
        Sort sort = Sort.by(wallItemPage.getSortDirection(), wallItemPage.getSortBy());
        Pageable pageable = PageRequest.of(wallItemPage.getPageNumber(),
                wallItemPage.getPageSize(), sort);

        return this.wallItemRepository.findAllUserWallItems(user.getId(), friendshipRepository.findUserFriends(user.getId()).stream()
                    .map(User::getId).collect(Collectors.toList()), pageable)
                .map(
                wallItem -> WallItemResponse.fromWallItemAndReactions(wallItem, this.reactionsRepository.findByIdWallElementID(wallItem.getId()))
        );

    }

    public Page<WallItemResponse> findAll(WallItemPage wallItemPage) {
        Sort sort = Sort.by(wallItemPage.getSortDirection(), wallItemPage.getSortBy());
        Pageable pageable = PageRequest.of(wallItemPage.getPageNumber(),
                wallItemPage.getPageSize(), sort);
        Page<WallItem> wallItems = this.wallItemRepository.findAll(pageable);

        return new PageImpl<>(wallItems.stream()
                .map(wallItem ->
                    WallItemResponse.fromWallItemAndReactions(wallItem, this.reactionsRepository.findByIdWallElementID(wallItem.getId()))
                )
                .collect(Collectors.toList()),
                pageable, wallItems.getTotalElements());
    }

    public Page<WallItemResponse> findUserWallItems(WallItemPage wallItemPage, Long userId) {
        Sort sort = Sort.by(wallItemPage.getSortDirection(), wallItemPage.getSortBy());
        Pageable pageable = PageRequest.of(wallItemPage.getPageNumber(),
                wallItemPage.getPageSize(), sort);
        Page<WallItem> wallItems = this.wallItemRepository.findAll(pageable);

        return new PageImpl<>(wallItems.stream()
                .filter(wallItem -> wallItem.getUser().getId().equals(userId))
                .map(wallItem ->
                        WallItemResponse.fromWallItemAndReactions(wallItem, this.reactionsRepository.findByIdWallElementID(wallItem.getId()))
                )
                .collect(Collectors.toList()),
                pageable, wallItems.getTotalElements());
    }

    public Optional<WallItem> getWallItemById(Long wallItemId) {
        return this.wallItemRepository.findById(wallItemId);
    }

}
