 package de.hybris.platform.customerreview.newcode;
 
 import java.util.Collections;
import java.util.List;

import javax.naming.directory.SearchResult;

import de.hybris.platform.customerreview.dao.CustomerReviewDao;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
 
 public class NewCustomerReviewDao
   extends AbstractItemDao
   implements CustomerReviewDao
 {

  // To get a product's total number of customer reviews whose ratings are within a given range
   
   public Integer getNumberOfReviewsByRating(Product item, int minRating, int maxRating)
   {
    String query = "SELECT {" + Item.PK + "} FROM {" + "CustomerReview" + "} WHERE {" + "product" + "}=?product AND {rating}>=?minRating AND {rating}<=?maxRating ORDER BY {" + "creationtime" + "} DESC";
    FlexibleSearchQuery fsQuery = new FlexibleSearchQuery(query);
    fsQuery.addQueryParameter("minRating", minRating);
    fsQuery.addQueryParameter("maxRating", maxRating);
    fsQuery.setResultClassList(Collections.singletonList(CustomerReviewModel.class));
    
    SearchResult<CustomerReviewModel> searchResult = getFlexibleSearchService().search(fsQuery);
    return searchResult.getResult().size();
  }
 }