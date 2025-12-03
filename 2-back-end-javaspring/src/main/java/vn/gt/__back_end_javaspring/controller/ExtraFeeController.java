//package vn.gt.__back_end_javaspring.controller;
//
//import org.springframework.web.bind.annotation.RestController;
//
//import lombok.RequiredArgsConstructor;
//import vn.gt.__back_end_javaspring.service.ExtraFeeService;
//
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//@RestController
//@RequestMapping("/api/extrafee")
//@RequiredArgsConstructor
//public class ExtraFeeController {
//    private final ExtraFeeService extraFeeService;
//
//    @GetMapping("")
//    public ResponseEntity<?> GetAllExtraFee() {
//        return ResponseEntity.ok().body(this.extraFeeService.GetAllExtraFee());
//    }
//
//    @PostMapping("/")
//    public ResponseEntity CreateExtraFee(@RequestBody ExtraFee entity) {
//        return ResponseEntity.ok().body(this.extraFeeService.CreateExtraFee(entity));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity GetExtraFee(@PathVariable("id") String id) {
//        return ResponseEntity.ok().body(this.extraFeeService.GetExtraFee(id));
//    }
//
//}
