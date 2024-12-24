package org.yapp.domain.profile.api.response;

import org.yapp.domain.profile.ProfileValue;
import org.yapp.domain.profile.ValueItem;

public record ProfileValueResponse(Long id, ValueItem valueItem, Integer selectedValue) {
  public static ProfileValueResponse from(ProfileValue profileValue) {
    return new ProfileValueResponse(profileValue.getId(), profileValue.getValueItem(),
        profileValue.getSelectedAnswer());
  }
}