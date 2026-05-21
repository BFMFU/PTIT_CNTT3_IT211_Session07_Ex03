Phân tích: BÀI 3 - BẢO MẬT ĐỘNG BẰNG CUSTOM ANNOTATION

1) Định nghĩa Input / Output
- Input: Các hàm giao dịch sẽ nhận các tham số khác nhau, trong đó các giao dịch nhạy cảm (withdraw, transfer) có thêm tham số OTP (kiểu String). OTP có thể là null, chuỗi rỗng, hoặc chuỗi mã do user nhập.
- Output: Các hàm trả về chuỗi mô tả kết quả (String). Ví dụ:
  - "Rút tiền thành công" / "Rút tiền thất bại: Sai OTP"
  - "Chuyển khoản thành công" / "Chuyển khoản thất bại: Sai OTP"
  - "Số dư của <user> là ..." (getBalance)

2) Yêu cầu nghiệp vụ & bẫy dữ liệu
- Giao dịch nhạy cảm (withdraw, transfer): BẮT BUỘC phải xác thực mã OTP.
- Giao dịch getBalance: KHÔNG cần OTP.
- Bẫy dữ liệu: Nếu OTP là null hoặc chuỗi rỗng thì phải trả về lỗi (không tiến hành giao dịch).

3) Tại sao dùng Custom Annotation (@RequiresOTP) tối ưu hơn quét theo tên method (pointcut by name)?
- Tính rõ ràng (Explicit): Annotation gắn trực tiếp lên phương thức cho biết rõ ràng mục đích (cần OTP). Người đọc code không phải nhớ quy ước đặt tên hay phải tra danh sách pointcut.
- Tránh phụ thuộc tên (Decoupling): Nếu quét theo tên (ví dụ method name startsWith("withdraw")), việc đổi tên hàm, refactor, hay có hàm mới với tên khác có thể làm bỏ sót. Annotation gắn với hàm sẽ di chuyển cùng khi refactor.
- Tái sử dụng & phân loại: Annotation có thể mở rộng (thêm thuộc tính, ví dụ mức độ bảo mật) để áp dụng hành vi khác nhau, dễ quản lý.
- Ít sai sót: Khi dev thêm phương thức mới cần OTP, chỉ cần thêm annotation, không cần chờ dev nhớ cập nhật pointcut hoặc đặt tên đúng.
- Đóng gói logic bảo mật: Logic bảo mật được ở một tầng riêng (Aspect), dễ bảo trì, test và audit.

Kết luận: Annotation cung cấp contract rõ ràng giữa business code và security aspect, giảm rủi ro khi refactor và làm cho intent rõ ràng hơn so với quét theo tên phương thức.

