package net.ecnu.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author LYW
 * @since 2022-07-25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData {

    /**
     * 总条目数
     */
    private long total;

    /**
     * 每页条目数
     */
    private long size;

    /**
     * 当前页
     */
    private long current;

    /**
     * 列表数据
     */
    private Object records;

    public PageData(long current, long size) {
        this.size = size;
        this.current = current;
    }

}
