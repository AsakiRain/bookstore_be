package com.cxsj1.homework.w5.controller;

import com.alibaba.fastjson2.JSON;
import com.cxsj1.homework.w5.model.Form.ProcessOrderForm;
import com.cxsj1.homework.w5.model.Order;
import com.cxsj1.homework.w5.utils.Res;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/manage/order/process")
public class Manage_ProcessOrder extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        ProcessOrderForm processOrderForm = JSON.parseObject(req.getInputStream().readAllBytes(),
                ProcessOrderForm.class);
        if (processOrderForm == null) {
            Res.Error(res, 400, 40002, "参数不足或有误");
            return;
        }

        String serial = processOrderForm.serial;
        if (!Order.hasOrder(serial)) {
            Res.Error(res, 404, 40401, "订单不存在");
            return;
        }

        if (processOrderForm.status == 3) {
            Res.Error(res, 400, 40000, "只有用户能确认收货");
            return;
        }

        if (processOrderForm.status > 3) {
            Res.Error(res, 400, 40000, "订单状态错误");
            return;
        }

        Order order = new Order(serial);

        order.set(processOrderForm);
        order.save();
        HashMap<String, Object> data = new HashMap<>() {
            {
                put("order_info", order);
            }
        };
        Res.Json(res, 20000, "成功更新状态", data);
    }
}