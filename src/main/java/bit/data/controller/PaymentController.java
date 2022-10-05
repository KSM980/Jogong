package bit.data.controller;


import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import bit.data.dto.OrderDto;
import bit.data.service.OrderServiceInter;

@Controller
public class PaymentController {
	@Autowired
	OrderServiceInter orderservice;
	
	@Autowired
	SqlSession sqlsession;
	
//	@GetMapping("/payview")
//	public String payview() {
//		return "/bit/payment/payview";
//	}
	
	@GetMapping("/GiftTestForm")
	public String GiftTestForm() {
		return "/bit/payment/GiftTestForm";
	}
	
	@RequestMapping(value = "/test.action", method = { RequestMethod.POST })
	@ResponseBody
	public void test(   
				@RequestBody String custom_data,
				HttpServletRequest request){

		 String imp_uid = request.getParameter("imp_uid");
		 String merchant_uid = request.getParameter("merchant_uid");
		 String pg = request.getParameter("pg");
		 String pay_method = request.getParameter("pay_method");
		 String name = request.getParameter("name");
		 String buyer_name = request.getParameter("buyer_name");
		 String amount = request.getParameter("amount");
		 String to_member_id = request.getParameter("custom_data[member_id]");
		 String count = request.getParameter("custom_data[count]");
		 String messagecard = request.getParameter("custom_data[messagecard]");
		 String banner = request.getParameter("custom_data[banner]");
		 String message = request.getParameter("custom_data[message]");
		 String success = request.getParameter("success");
		 String buyer_tel = request.getParameter("buyer_tel");
         String buyer_addr = request.getParameter("custom_data[buyer_addr]");
         String buyer_postcode = request.getParameter("custom_data[buyer_postcode]");
         String userNum = request.getParameter("custom_data[userNum]");
         String orderDetailNum = request.getParameter("custom_data[orderDetailNum]");
		 
		 OrderDto orderDto = new OrderDto(); 
		 orderDto.setImp_uid(imp_uid);
		 orderDto.setTotalPrice(Integer.parseInt(amount));
		 orderDto.setPg(pg);
		 orderDto.setPay_method(pay_method);
  		 orderDto.setMessagecard(messagecard);
		 orderDto.setBanner(banner);
		 orderDto.setMessage(message);
		 orderDto.setOrderStatus(success);
		 orderDto.setUserNum(Integer.parseInt(userNum));
		 orderDto.setOrderDetailNum(Integer.parseInt(orderDetailNum));
		 orderDto.setHp(buyer_tel);
		 orderDto.setDeliveryAddress(buyer_addr);
		 orderDto.setPostalcode(buyer_postcode);
		 orderDto.setCount(Integer.parseInt(count));
		 
		orderservice.insertOrder(orderDto);
		
	}
	
	@GetMapping("/payview")
	public ModelAndView payread(int num) {
		
		String buyer_name = orderservice.getfriendNickNameSearch(num);
		String to_member_id = orderservice.getNickNameSearch(num);
		String sangpum = orderservice.getItemNameSearch(num);
		Integer price = orderservice.getItemPriceSearch(num);
		String thumbnailImage = orderservice.getItemThumbnailSearch(num);
		Integer count = orderservice.getCount(num);
		Integer userNum = orderservice.getUserNum(num);
		Integer orderDetailNum = orderservice.getOrderDetailNum(num);
		
		Integer totalprice = price * count;
		
		
		
		ModelAndView mview = new ModelAndView();
		
		mview.addObject("buyer",buyer_name);
		mview.addObject("to_member_id",to_member_id);
		mview.addObject("productName",sangpum);
		mview.addObject("price",price);
		mview.addObject("thumbnailImage",thumbnailImage);
		mview.addObject("count",count);
		mview.addObject("totalprice",totalprice);
		mview.addObject("userNum",userNum);
		mview.addObject("orderDetailNum",orderDetailNum);
		
		mview.setViewName("/bit/payment/payview");
		
		return mview;
	}
	

}
