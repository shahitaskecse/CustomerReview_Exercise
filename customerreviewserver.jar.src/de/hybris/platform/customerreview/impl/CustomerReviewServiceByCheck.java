package de.hybris.platform.customerreview.newcode;

import java.util.Set;

import de.hybris.platform.customerreview.constants.GeneratedCustomerReviewConstants.Attributes.Product;
import de.hybris.platform.customerreview.constants.GeneratedCustomerReviewConstants.Attributes.User;
import de.hybris.platform.customerreview.dao.CustomerReviewDao;
import de.hybris.platform.customerreview.impl.CustomerReviewModel;
import de.hybris.platform.customerreview.impl.DefaultCustomerReviewService;
import de.hybris.platform.customerreview.impl.ProductModel;
import de.hybris.platform.customerreview.impl.Required;
import de.hybris.platform.customerreview.impl.UserModel;
import de.hybris.platform.customerreview.jalo.CustomerReview;
import de.hybris.platform.customerreview.jalo.CustomerReviewManager;

public class CustomerReviewServiceByCheck extends DefaultCustomerReviewService {
	private NewCustomerReviewDao customerReviewDao;

	private ReadFromConfig readFromConfig;

	private Set<String> cursedWords;

	protected NewCustomerReviewDao getCustomerReviewDao() {
		return this.customerReviewDao;
	}

	@Required
	public void setCustomerReviewDao(CustomerReviewDao customerReviewDao) {
		this.customerReviewDao = customerReviewDao;
	}

	protected ReadFromConfig getReadFromConfig() {
		return this.readFromConfig;
	}

	@Required
	public void setCustomerReviewDao(ReadFromConfig readFromConfig) {
		this.readFromConfig = readFromConfig;
		cursedWords = readFromConfig.getFromConfig();
	}

	public CustomerReviewModel createCustomerReview(Double rating, String headline, String comment, UserModel user, ProductModel product)
   {
   		//check if the comment contains curse words
		 String[] splittedComments = comment.split(" ,");
		 for(String splitComment:splittedComments) {
			 if(cursedWords.contains(splitComment)) {
				 throw new JaloInvalidParameterException("Cursed word present", 0);. 
			 }
		 }

		 //check if the rating is < 0
		 if ((rating.doubleValue() < CustomerReviewConstants.getInstance().MINRATING)) {
		 	throw new JaloInvalidParameterException(Localization.getLocalizedString("error.customerreview.invalidrating", 

		 }

		 //create customer reviews if all the tests are passed
	     CustomerReview review = CustomerReviewManager.getInstance().createCustomerReview(rating, headline, comment, 
	       (User)getModelService().getSource(user), (Product)getModelService().getSource(product));
	     return (CustomerReviewModel)getModelService().get(review);
   }
	
	public int getReviewsForProductAndLanguage(int minRating, int maxRating) {
		return getCustomerReviewDao().getNumberOfReviewsByRating(minRating,maxRating);
	}
	
	
	
	

}
