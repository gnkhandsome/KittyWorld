package com.kitty.kitty_base.views.zloading;


import com.kitty.kitty_base.views.zloading.ball.ElasticBallBuilder;
import com.kitty.kitty_base.views.zloading.ball.InfectionBallBuilder;
import com.kitty.kitty_base.views.zloading.ball.IntertwineBuilder;
import com.kitty.kitty_base.views.zloading.circle.DoubleCircleBuilder;
import com.kitty.kitty_base.views.zloading.circle.PacManBuilder;
import com.kitty.kitty_base.views.zloading.circle.RotateCircleBuilder;
import com.kitty.kitty_base.views.zloading.circle.SingleCircleBuilder;
import com.kitty.kitty_base.views.zloading.circle.SnakeCircleBuilder;
import com.kitty.kitty_base.views.zloading.clock.CircleBuilder;
import com.kitty.kitty_base.views.zloading.clock.ClockBuilder;
import com.kitty.kitty_base.views.zloading.path.MusicPathBuilder;
import com.kitty.kitty_base.views.zloading.path.SearchPathBuilder;
import com.kitty.kitty_base.views.zloading.path.StairsPathBuilder;
import com.kitty.kitty_base.views.zloading.rect.ChartRectBuilder;
import com.kitty.kitty_base.views.zloading.rect.StairsRectBuilder;
import com.kitty.kitty_base.views.zloading.star.LeafBuilder;
import com.kitty.kitty_base.views.zloading.star.StarBuilder;
import com.kitty.kitty_base.views.zloading.text.TextBuilder;

public enum Z_TYPE
{
    CIRCLE(CircleBuilder.class),
    CIRCLE_CLOCK(ClockBuilder.class),
    STAR_LOADING(StarBuilder.class),
    LEAF_ROTATE(LeafBuilder.class),
    DOUBLE_CIRCLE(DoubleCircleBuilder.class),
    PAC_MAN(PacManBuilder.class),
    ELASTIC_BALL(ElasticBallBuilder.class),
    INFECTION_BALL(InfectionBallBuilder.class),
    INTERTWINE(IntertwineBuilder.class),
    TEXT(TextBuilder.class),
    SEARCH_PATH(SearchPathBuilder.class),
    ROTATE_CIRCLE(RotateCircleBuilder.class),
    SINGLE_CIRCLE(SingleCircleBuilder.class),
    SNAKE_CIRCLE(SnakeCircleBuilder.class),
    STAIRS_PATH(StairsPathBuilder.class),
    MUSIC_PATH(MusicPathBuilder.class),
    STAIRS_RECT(StairsRectBuilder.class),
    CHART_RECT(ChartRectBuilder.class),;

    private final Class<?> mBuilderClass;

    Z_TYPE(Class<?> builderClass)
    {
        this.mBuilderClass = builderClass;
    }

    <T extends ZLoadingBuilder> T newInstance()
    {
        try
        {
            return (T) mBuilderClass.newInstance();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
