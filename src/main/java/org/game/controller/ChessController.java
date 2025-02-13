package org.game.controller;

import org.game.PieceOut;
import org.game.service.ChessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chess")
public class ChessController {

    @Autowired
    private ChessService chessService;

    @CrossOrigin
    @GetMapping
    public List<PieceOut> move(@RequestParam("move") String command) {
        return chessService.move(command);
    }

}
