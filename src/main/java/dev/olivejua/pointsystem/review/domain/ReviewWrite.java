package dev.olivejua.pointsystem.review.domain;

import dev.olivejua.pointsystem.common.service.DateTimeHolder;
import dev.olivejua.pointsystem.common.util.StringUtil;
import dev.olivejua.pointsystem.order.domain.Order;
import dev.olivejua.pointsystem.user.domain.User;
import lombok.Getter;

@Getter
public class ReviewWrite {
    private final Order order;
    private final User writer;
    private final String title;
    private final String content;

    public ReviewWrite(Order order, User writer, String title, String content) {
        this.order = order;
        this.writer = writer;
        this.title = title;
        this.content = content;
    }

    public boolean isSameWriterAsBuyer() {
        return order.getCustomer().isSameAs(writer);
    }

    public long getOrderId() {
        return order.getId();
    }

    public long getWriterId() {
        return writer.getId();
    }

    public boolean hasInvalidTitleOrContent() {
        return StringUtil.isNullOrBlank(title) ||
                StringUtil.isNullOrBlank(content);
    }

    public boolean hasOrderWithCancellation() {
        return order.isCanceled();
    }

    public boolean hasOrderCreatedBeforeThanAMonth(DateTimeHolder dateTimeHolder) {
        return order.getCreatedAt().isBefore(dateTimeHolder.now().minusMonths(1));
    }
}
