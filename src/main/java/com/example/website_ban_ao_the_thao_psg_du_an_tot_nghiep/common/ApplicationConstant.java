package com.example.website_ban_ao_the_thao_ps.common;


/*
 Ghi chú
1. Sản phẩm, khách hàng, nhân viên
   ACTIVE là hoạt động
   INACTIVE là không hoạt động
2. Chi tiết sản phẩm
   ACTIVE là hoạt động
   INACTIVE là không hoạt động
   PENDING là chưa xác nhận
3. Địa chỉ
   DEFAULT là địa chỉ mặc định
   EXTRA là điạ chỉ phụ
4. Hình thức thanh toán
   CASH là tiền mặt
   BANKING là chuyển khoản
   CASHANDBANKING là vừa chuyển khoản vừa tiền mặt
7. Loại lịch sử hoá đơn
   CREATED  là tạo đơn hàng
   CONFIRMED là đã xác nhận thông tin thanh toán
   SHIPPING là đã giao cho đơn vị vận chuyển
   APPROVED là đã nhận đơn hàng
   CANCELLED là đơn hàng đã huỷ
   EDITED là chỉnh sửa đơn hàng
   REVERSED là hoàn trả
8. Trạng thái hoá đơn
   PENDING_CHECKOUT là chờ thanh toán
   PENDING là đang chờ xác nhận
   CONFIRMED là đã xác nhận
   SHIPPING là đang vận chuyển
   CANCELLED là đã huỷ
   APPROVED là đã hoàn thành
   REVERSE là đã trả hàng
   PAYMENT_SUCCESS thanh toan thanh cong
9. Trạng thái hoá đơn chi tiết
   APPROVED là đã xác nhận
   REVERSE là đã hoàn trả
10. Trạng thái giỏ hàng
    PENDING là đang chờ,
    CANCELLED là đã huỷ
11. Hình thức bán hàng
    STORE là bán tại cửa hàng
    DELIVERY là bán taị cửa hàng và giao hàng
    ONLINE là bán trên web
 */


public class    ApplicationConstant {

    public enum TrangThaiSanPham {
        ACTIVE, INACTIVE
    }

    public enum TrangThaiChiTietSanPham {
        ACTIVE, INACTIVE, PENDING
    }

    public enum TrangThaiQuyDinh {
        ACTIVE, INACTIVE
    }

    public enum TrangThaiVaiTro {
        ACTIVE, INACTIVE
    }

    public enum TrangThaiThuHang {
        ACTIVE, INACTIVE
    }

    public enum TrangThaiChuongTrinhTichDiem {
        ACTIVE, INACTIVE
    }

    public enum TrangThaiTiLeQuyDoiThuHang {
        ACTIVE, INACTIVE
    }

    public enum TrangThaiQuyDoiDiem {
        ACTIVE, INACTIVE
    }

    public enum TrangThaiDiaChi {
        DEFAULT, EXTRA
    }

    public enum TrangThaiTaiKhoan {
        ACTIVE, INACTIVE
    }

//
//    public enum TrangThaiVoucher {
//        PENDING("PENDING", "Sắp Diễn Ra", "warning"), ACTIVE("ACTIVE", "Hoạt động", "primary"), INACTIVE("INACTIVE", "Không hoạt động", "error");
//    }

    public enum HinhThucThanhToan {
        CASH, BANKING, CASHANDBANKING
    }

    public enum HinhThucBanHang {
        STORE, DELIVERY, ONLINE
    }

    public enum TrangThaiHoaDon {
        PENDING_CHECKOUT,PAYMENT_SUCCESS, PENDING, CONFIRMED, SHIPPING, CANCELLED, APPROVED, REVERSE, PENDING_CANCELLED
    }

    public enum LoaiLichSuHoaDon {
        CREATED, CONFIRMED, SHIPPING, APPROVED, CANCELLED, EDITED, REVERSED
    }

    public enum TrangThaiHoaDonChiTiet {
        APPROVED, REVERSE
    }

    public enum TrangThaiGioHang {
        PENDING, CANCELLED
    }

}
