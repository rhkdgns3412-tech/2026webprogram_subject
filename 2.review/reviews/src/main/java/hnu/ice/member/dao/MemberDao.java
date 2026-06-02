package hnu.ice.member.dao;

import hnu.ice.common.DBConnection;
import hnu.ice.member.model.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {

    public boolean register(Member member) {
        String sql = "INSERT INTO users (user_id, password, nickname, email) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, member.getUserId());
            pstmt.setString(2, member.getPassword());
            pstmt.setString(3, member.getNickname());
            pstmt.setString(4, member.getEmail());

            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            throw new RuntimeException("회원 등록에 실패했습니다.", e);
        }
    }

    public Member login(String userId, String password) {
        String sql = "SELECT user_id, password, nickname, email FROM users WHERE user_id = ? AND password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setUserId(rs.getString("user_id"));
                    member.setPassword(rs.getString("password"));
                    member.setNickname(rs.getString("nickname"));
                    member.setEmail(rs.getString("email"));
                    return member;
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("로그인 처리 중 오류가 발생했습니다.", e);
        }
    }

    public Member getMember(String userId) {
        String sql = "SELECT user_id, password, nickname, email FROM users WHERE user_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, userId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Member member = new Member();
                    member.setUserId(rs.getString("user_id"));
                    member.setPassword(rs.getString("password"));
                    member.setNickname(rs.getString("nickname"));
                    member.setEmail(rs.getString("email"));
                    return member;
                }
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException("회원 조회 중 오류가 발생했습니다.", e);
        }
    }
}
