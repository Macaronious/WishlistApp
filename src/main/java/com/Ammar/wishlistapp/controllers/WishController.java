package com.Ammar.wishlistapp.controllers;

import com.Ammar.wishlistapp.models.User;
import com.Ammar.wishlistapp.models.Wish;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.Ammar.wishlistapp.repositories.WishRepository;
import com.Ammar.wishlistapp.repositories.UserRepository;

import java.util.List;

@Controller
public class WishController {

    private final WishRepository wishRepository;
    private final UserRepository userRepository;

    public WishController(WishRepository wishRepository,
                          UserRepository userRepository) {
        this.wishRepository = wishRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/wishlist")
    public String showWishlist(Model model,
                               jakarta.servlet.http.HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("wishes", wishRepository.findByUserOrderByPosition(user));
        return "wishlist";
    }

    @PostMapping("/add")
    public String addWish(@RequestParam String name,
                          @RequestParam(required = false) String description,
                          jakarta.servlet.http.HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (description == null) {
            description = "";
        }

        Wish wish = new Wish(name, description, user);
        wishRepository.save(wish);

        return "redirect:/wishlist";
    }

    @PostMapping("/delete")
    public String deleteWish(@RequestParam Long id,
                             jakarta.servlet.http.HttpSession session) {

        User user = (User) session.getAttribute("user");

        Wish wish = wishRepository.findById(id).orElse(null);

        if (wish != null && wish.getUser().getId().equals(user.getId())) {
            wishRepository.deleteById(id);
        }

        return "redirect:/wishlist";
    }

    @GetMapping("/edit")
    public String editWish(@RequestParam Long id, Model model) {
        Wish wish = wishRepository.findById(id).orElse(null);
        model.addAttribute("wish", wish);
        return "edit";
    }

    @PostMapping("/update")
    public String updateWish(@RequestParam Long id,
                             @RequestParam String name,
                             @RequestParam String description) {

        Wish wish = wishRepository.findById(id).orElse(null);

        if (wish != null) {
            wish.setName(name);
            wish.setDescription(description);
            wishRepository.save(wish);
        }

        return "redirect:/wishlist";
    }

    @GetMapping("/wishlist/{userId}")
    public String viewWishlist(@PathVariable Long userId, Model model) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("wishes", wishRepository.findByUser(user));
        model.addAttribute("owner", user.getUsername());

        return "public-wishlist";
    }

    @PostMapping("/updateOrder")
    @ResponseBody
    public void updateOrder(@RequestBody List<Long> ids) {

        for (int i = 0; i < ids.size(); i++) {
            Wish wish = wishRepository.findById(ids.get(i)).orElse(null);
            if (wish != null) {
                wish.setPosition(i);
                wishRepository.save(wish);
            }
        }
    }

    @PostMapping("/reserve")
    public String toggleReserve(@RequestParam Long id,
                                @RequestParam(required = false) String name) {

        Wish wish = wishRepository.findById(id).orElse(null);

        if (wish != null) {

            if (!wish.isReserved()) {
                wish.setReserved(true);

                if (name == null || name.isEmpty()) {
                    wish.setReservedBy("Anonym");
                } else {
                    wish.setReservedBy(name);
                }

            } else {
                wish.setReserved(false);
                wish.setReservedBy(null);
            }

            wishRepository.save(wish);
            return "redirect:/wishlist/" + wish.getUser().getId();
        }

        return "redirect:/wishlist";
    }

}