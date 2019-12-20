package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;

import entity.Cart;
import entity.Item;
import entity.Order;
import entity.Product;

public interface ProductDao {

	List<Product> selectAll();

	Product selectById(Integer id);

	Cart selectItemFromCartByUserIdAndProductId(@Param("userid") Integer userId, @Param("productid") Integer productId);

	List<Cart> selectCartByUserId(Integer userId);

	void deleteCart(@Param("userId") Integer userId, @Param("productId") Integer productId);

	Integer insertOrder(Order o);

	void insertIntoCart(Cart c);

	void updateCart(Cart c);

	void insertIntoItem(Item i);

}
