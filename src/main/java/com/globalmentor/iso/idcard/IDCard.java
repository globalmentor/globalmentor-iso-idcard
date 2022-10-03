/*
 * Copyright © 1996-2008 GlobalMentor, Inc. <https://www.globalmentor.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.globalmentor.iso.idcard;

import java.util.*;

import com.globalmentor.model.*;

/**
 * An encapsulation of an ISO identity card, such as as defined in ISO/IEC 7812-1:2000(E),
 * "Identification cards — Identification of issuers — Part 1: Numbering system". This implementation recognizes common types of ID cards, such as MasterCard
 * and Visa credit cards, from the Primary Account Number (PAN).
 * @author Garret Wilson
 */
public class IDCard {

	/** The list of ranges indicating products, in increasing order of priority. The list is only thread-safe for reading. */
	protected static final List<NameValuePair<Range<Integer>, Product>> iinRangeProducts = new ArrayList<NameValuePair<Range<Integer>, Product>>();

	/**
	 * Determines the product from the given Primary Account Number.
	 * @param pan The Primary Account Number (PAN).
	 * @return The product this PAN represents, or <code>null</code> if a corresponding product could not be determined.
	 * @throws NullPointerException if the given PAN is <code>null</code>.
	 */
	public static final Product getProduct(final PAN pan) {
		final Integer iin = Integer.valueOf(pan.getIIN()); //get the PAN as an Integer
		for(final NameValuePair<Range<Integer>, Product> iinRangeProduct : iinRangeProducts) { //for each product range
			if(iinRangeProduct.getName().contains(iin)) { //if the IIN is in this range
				return iinRangeProduct.getValue(); //return the product
			}
		}
		return null; //we couldn't find a matching product
	}

	/** Initializes the product IIN ranges. */
	static {
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(352800, 358999), Product.JCB)); //JCB (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(370000, 379999), Product.AMERICAN_EXPRESS)); //American Express (http://www125.americanexpress.com/merchant/oam/resources/POS499.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(490303, 490303), Product.MAESTRO)); //UK Maestro (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(493698, 493699), Product.MAESTRO)); //UK Maestro (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)		
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(400000, 499999), Product.VISA)); //Visa (http://www125.americanexpress.com/merchant/oam/resources/POS499.pdf) (note conflict with 490303 and 493698-99 UK Maestro ranges)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(500000, 509999), Product.MAESTRO)); //MasterCard (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)		
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(510000, 559999), Product.MASTERCARD)); //MasterCard (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(560000, 589999), Product.MAESTRO)); //MasterCard (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(601100, 601199), Product.DISCOVER)); //Discover (http://www.discoverbiz.com/resources/data/card_present.html)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(633450, 633460), Product.SOLO)); //Solo (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)	
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(633462, 633472), Product.SOLO)); //Solo (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(633474, 633475), Product.SOLO)); //Solo (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(633477, 633477), Product.SOLO)); //Solo (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(633479, 633480), Product.SOLO)); //Solo (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(633482, 633489), Product.SOLO)); //Solo (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(633498, 633498), Product.SOLO)); //Solo (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(676700, 676799), Product.SOLO)); //Solo (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf)
		iinRangeProducts.add(new NameValuePair<Range<Integer>, Product>(new Range<Integer>(600000, 699999), Product.MAESTRO)); //MasterCard (http://www.barclaycardmerchantservices.co.uk/existing_customers/operational/pdf/binranges.pdf) (note conflict with Discover and Solo ranges)				
	}
}
