package ihotel.app.repository.search;

import ihotel.app.domain.DGoods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DGoods} entity.
 */
public interface DGoodsSearchRepository extends ElasticsearchRepository<DGoods, Long> {}
