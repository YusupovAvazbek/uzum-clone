package uz.nt.uzumclone.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.nt.uzumclone.dto.ColorDto;
import uz.nt.uzumclone.dto.ResponseDto;
import uz.nt.uzumclone.service.Impl.ColorServiceImpl;

import java.util.List;

@RestController
@RequestMapping("color")
@RequiredArgsConstructor
public class ColorResources {
    private final ColorServiceImpl colorService;

    @PostMapping("add")
    public ResponseDto<ColorDto> add(@RequestBody ColorDto colorDto){
        return colorService.add(colorDto);
    }

    @GetMapping("{id}")
    public ResponseDto<ColorDto> getById(@PathVariable Integer id){
        return colorService.getById(id);
    }

    @GetMapping()
    public ResponseDto<List<ColorDto>> getAll(){
        return colorService.getAll();
    }
}
