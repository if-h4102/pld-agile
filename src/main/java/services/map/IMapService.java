package services.map;

import models.Intersection;

import java.util.concurrent.CompletableFuture;

public interface IMapService {
    CompletableFuture<Intersection> promptIntersection();
}
