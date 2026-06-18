-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- 생성 시간: 26-06-17 02:42
-- 서버 버전: 10.4.32-MariaDB
-- PHP 버전: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 데이터베이스: `pruduct_db`
--

-- --------------------------------------------------------

--
-- 테이블 구조 `product`
--

CREATE TABLE `product` (
  `product_id` text NOT NULL,
  `seller_id` text NOT NULL,
  `title` text NOT NULL,
  `description` text DEFAULT NULL,
  `price` int(11) NOT NULL,
  `category` enum('교재','의류','필기구','기타') NOT NULL DEFAULT '기타',
  `image_path` varchar(255) DEFAULT NULL,
  `status` enum('판매중','판매완료','예약중') NOT NULL DEFAULT '판매중',
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 테이블의 덤프 데이터 `product`
--

INSERT INTO `product` (`product_id`, `seller_id`, `title`, `description`, `price`, `category`, `image_path`, `status`, `created_at`) VALUES
('ㅁㄴㅇㄹ', 'ㅁㄴㅇㄹ', 'ㅁㄴㅇㄹ', 'ㅁㄴㅇ', 561, '교재', 'uploads/product/_____07c9a0095cc24790b0528469fd5ec3b6.jpg', '판매중', '2026-06-17 05:28:47'),
('수학책', '광훈', '수학책팜', 'ㅇ', 150000, '교재', 'uploads/product/____1974b49503fd4534ab0f05e2f8a23de6.gif', '판매중', '2026-06-17 05:40:21'),
('ㅁㄴㅇㄹ', 'ㅁㄴㅇㄹ', 'ㅁㄴㅇㄹ', 'ㅁㄴㅇ', 561, '교재', 'uploads/product/_____07c9a0095cc24790b0528469fd5ec3b6.jpg', '판매중', '2026-06-17 06:06:22'),
('ㅁㄴㅇ', 'ㅁㄴㅇ', 'ㅁㄴㅇ', 'ㅁㄴㅇ', 1500, '교재', 'uploads/product/____09e9e976294247508f5f470fe47bcd0e.jpg', '판매중', '2026-06-17 06:15:48'),
('티셔츠', '광훈', '옷팝니다', 'ㅇㅇ', 500, '의류', 'uploads/product/____0c57f60c55844bc8b3cc09f75ef53c51.jpg', '판매중', '2026-06-17 06:18:04');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
