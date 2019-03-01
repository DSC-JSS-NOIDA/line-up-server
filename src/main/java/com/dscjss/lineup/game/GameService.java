package com.dscjss.lineup.game;

public interface GameService {

    boolean isValidScan(String username, String code, double lat, double lng);
}
