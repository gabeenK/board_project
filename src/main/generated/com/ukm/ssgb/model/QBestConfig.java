package com.ukm.ssgb.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBestConfig is a Querydsl query type for BestConfig
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBestConfig extends EntityPathBase<BestConfig> {

    private static final long serialVersionUID = -1040721830L;

    public static final QBestConfig bestConfig = new QBestConfig("bestConfig");

    public final BooleanPath active = createBoolean("active");

    public final NumberPath<Long> bestConfigId = createNumber("bestConfigId", Long.class);

    public final NumberPath<Integer> bestSize = createNumber("bestSize", Integer.class);

    public QBestConfig(String variable) {
        super(BestConfig.class, forVariable(variable));
    }

    public QBestConfig(Path<? extends BestConfig> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBestConfig(PathMetadata metadata) {
        super(BestConfig.class, metadata);
    }

}

