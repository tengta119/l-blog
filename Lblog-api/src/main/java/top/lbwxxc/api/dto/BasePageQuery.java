package top.lbwxxc.api.dto;


import lombok.Data;

@Data
public class BasePageQuery {

    private int current = 1;

    private int size = 10;
}
