package uz.nt.uzumclone.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.nt.uzumclone.dto.UsersDto;
import uz.nt.uzumclone.model.Users;

@Mapper(componentModel = "spring")
public abstract class UsersMapper implements CommonMapper<UsersDto, Users>{
    @Override
    @Mapping(target = "isActive", expression = "java((short) 1)")
    @Mapping(target = "enabled",expression = "java(true)")
    @Mapping(target= "role",expression = "java(\"USER\")")
    abstract public Users toEntity(UsersDto dto);
    @Override
    abstract public UsersDto toDto(Users entity);
}
