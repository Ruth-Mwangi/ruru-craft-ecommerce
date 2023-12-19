package com.ruth.rurucraftsecommerce.options;

import com.ruth.rurucraftsecommerce.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@Tag(name = "5. Option & Option Groups ", description = "Interact with options and option groups in a system. You can create new ones, modify existing ones, list, or remove ones that are no longer necessary.")

@RestController
@RequestMapping("/ruru-crafts")
public class OptionController {

    public Locale locale = LocaleContextHolder.getLocale();

    @Autowired
    private OptionServiceImpl optionService;

    @Autowired
    private MessageSource messageSource;

    @Operation(summary = "This endpoint gets all options groups and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/option-groups")
    public ResponseEntity<?> getAllOptionGroups(){
        try {
            List<OptionGroup> optionGroup=optionService.getAllOptionGroups();
            Response response=new Response(HttpStatus.OK.value(), messageSource.getMessage("get.success.message",new Object[]{OptionGroup.class.getSimpleName()},optionService.locale),optionGroup );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint gets all options and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/options")
    public ResponseEntity<?> getAllOptions(){
        try {
            List<Option> option=optionService.getAllOptions();
            Response response=new Response(HttpStatus.OK.value(), messageSource.getMessage("get.success.message",new Object[]{Option.class.getSimpleName()},optionService.locale),option );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint gets  option group by id and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/option-group/{id}")
    public ResponseEntity<?> getOptionGroupById(@PathVariable("id") Integer id){
        try {
            OptionGroup optionGroup=optionService.getOptionGroupById(id);
            Response response=new Response(HttpStatus.OK.value(), messageSource.getMessage("get.success.message",new Object[]{OptionGroup.class.getSimpleName()},optionService.locale),optionGroup );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint gets option by id and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping("/option/{id}")
    public ResponseEntity<?> getOptionById(@PathVariable("id") Integer id){
        try {
            Option option=optionService.getOptionById(id);
            Response response=new Response(HttpStatus.OK.value(), messageSource.getMessage("get.success.message",new Object[]{Option.class.getSimpleName()},optionService.locale),option );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint creates an  option group and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/option-group/create")
    public ResponseEntity<?> createOptionGroup(@RequestBody OptionGroup optionGroup){
        try {
            OptionGroup createdOptionGroup=optionService.createOptionGroup(optionGroup);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("create.success.message",new Object[]{OptionGroup.class.getSimpleName()},optionService.locale),createdOptionGroup );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint creates an option and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PostMapping("/option/create")
    public ResponseEntity<?> createOption(@RequestBody Option option){
        try {
            Option createdOption=optionService.createOption(option);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("create.success.message",new Object[]{Option.class.getSimpleName()},optionService.locale),createdOption );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint updates an  option group and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/option-group/update/{id}")
    public ResponseEntity<?> updateOptionGroup(@PathVariable("id") Integer id,@RequestBody OptionGroup optionGroup){
        try {
            OptionGroup createdOptionGroup=optionService.updateOptionGroup(id,optionGroup);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("update.success.message",new Object[]{OptionGroup.class.getSimpleName()},optionService.locale),createdOptionGroup );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint update an option and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping("/option/update/{id}")
    public ResponseEntity<?> updateOption(@PathVariable("id") Integer id,@RequestBody Option option){
        try {
            Option createdOption=optionService.updateOption(id,option);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("update.success.message",new Object[]{Option.class.getSimpleName()},optionService.locale),createdOption );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint deletes an  option group and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/option-group/delete/{id}")
    public ResponseEntity<?> deleteOptionGroup(@PathVariable("id") Integer id){
        try {
            boolean deleted=optionService.deleteOptionGroupById(id);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("delete.success.message",new Object[]{OptionGroup.class.getSimpleName()},optionService.locale) );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @Operation(summary = "This endpoint update an option and requires user to be authenticated")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/option/delete/{id}")
    public ResponseEntity<?> deleteOption(@PathVariable("id") Integer id){
        try {
            boolean deleted=optionService.deleteOptionById(id);
            Response response=new Response(HttpStatus.OK.value(),
                    messageSource.getMessage("delete.success.message",new Object[]{Option.class.getSimpleName()},optionService.locale) );
            return ResponseEntity.ok(response);

        }catch (NoSuchElementException e){
            Response response= new Response(HttpStatus.NOT_FOUND.value(), e.getMessage() );
            return ResponseEntity.badRequest().body(response);
        }
    }
}
