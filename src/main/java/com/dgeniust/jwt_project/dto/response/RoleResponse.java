package com.dgeniust.jwt_project.dto.response;

import java.util.Set;

public class RoleResponse {
    String name;
    String description;
    Set<PermissionResponse> permissions;
}
