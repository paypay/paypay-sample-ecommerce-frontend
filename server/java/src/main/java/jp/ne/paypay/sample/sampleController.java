package jp.ne.paypay.sample;

import jp.ne.paypay.sample.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class sampleController {

    @GetMapping("/cakes")
    @ResponseBody
    public ResponseEntity<List<Item>> getCakes() {
        List<Item> items = new ArrayList();
        items.add(new Item(1, 120, "cake_shop.mississippi", "darkforest.png"));
        items.add(new Item(2, 190, "cake_shop.red_velvet", "redvelvet.png"));
        items.add(new Item(3, 100, "cake_shop.dark_forest", "darkforestcake.png"));
        items.add(new Item(4, 200, "cake_shop.rainbow", "rainbow.png"));
        items.add(new Item(5, 80, "cake_shop.lemon", "lemon.png"));
        items.add(new Item(6, 110, "cake_shop.pineapple", "pineapple.png"));
        items.add(new Item(7, 90, "cake_shop.banana", "banana.png"));
        items.add(new Item(8, 165, "cake_shop.carrot", "carrot.png"));
        items.add(new Item(9, 77, "cake_shop.choco", "choco.png"));
        items.add(new Item(10, 130, "cake_shop.chocochip", "chocochip.png"));
        items.add(new Item(11, 140, "cake_shop.orange", "orange.png"));
        items.add(new Item(12, 155, "cake_shop.butterscotch", "butterscotch.png"));
        return ResponseEntity.ok(items);
    }
}
