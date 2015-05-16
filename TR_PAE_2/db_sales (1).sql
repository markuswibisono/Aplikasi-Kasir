-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 21 Mar 2015 pada 18.58
-- Versi Server: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `db_sales`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `barang`
--

CREATE TABLE IF NOT EXISTS `barang` (
  `Id_Barang` varchar(30) NOT NULL,
  `Nama_Barang` varchar(30) NOT NULL,
  `Harga` varchar(30) NOT NULL,
  `Group_Id` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `barang`
--

INSERT INTO `barang` (`Id_Barang`, `Nama_Barang`, `Harga`, `Group_Id`) VALUES
('FC01', 'Ponds', '10000', 'FC'),
('SBG011', 'Detol', '10000', 'SBG'),
('SBG012', 'LifeBoy', '5000', 'SBG'),
('FC02', 'Biore2346', '50000', 'FC'),
('FC03', 'nyoba23', '5000', 'FC');

-- --------------------------------------------------------

--
-- Struktur dari tabel `group_barang`
--

CREATE TABLE IF NOT EXISTS `group_barang` (
  `Group_Id` varchar(30) NOT NULL,
  `Nama_Group` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `group_barang`
--

INSERT INTO `group_barang` (`Group_Id`, `Nama_Group`) VALUES
('FC', 'Facial'),
('SBG', 'Sabun Gosok');

-- --------------------------------------------------------

--
-- Struktur dari tabel `group_user`
--

CREATE TABLE IF NOT EXISTS `group_user` (
  `Group_Id` varchar(30) NOT NULL,
  `Keterangan_User` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `group_user`
--

INSERT INTO `group_user` (`Group_Id`, `Keterangan_User`) VALUES
('0', 'Admin'),
('1', 'Owner'),
('2', 'Cashier');

-- --------------------------------------------------------

--
-- Struktur dari tabel `pelanggan`
--

CREATE TABLE IF NOT EXISTS `pelanggan` (
  `Id_Pelanggan` varchar(30) NOT NULL,
  `Nama_Pelanggan` varchar(30) NOT NULL,
  `Alamat_Pelanggan` varchar(30) NOT NULL,
  `Id_Utang` int(11) NOT NULL,
  `Jenis_Kelamin` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `pelanggan`
--

INSERT INTO `pelanggan` (`Id_Pelanggan`, `Nama_Pelanggan`, `Alamat_Pelanggan`, `Id_Utang`, `Jenis_Kelamin`) VALUES
('P01', 'Markus23', 'KFCs', 0, 'Laki-laki'),
('P02', 'Bagas', 'KFC', 0, 'Laki-laki'),
('P03', 'Kukuh', 'KFCapa', 0, 'Laki-laki'),
('P04', 'nyoba32', 'KFC', 0, 'Laki-laki'),
('P05', 'Willy2', 'KFC2', 0, 'Laki-laki'),
('0', '', '', 0, '');

-- --------------------------------------------------------

--
-- Struktur dari tabel `stock`
--

CREATE TABLE IF NOT EXISTS `stock` (
  `Id_Barang` varchar(30) NOT NULL,
  `Id_Toko` varchar(30) NOT NULL,
  `QTY_Barang` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `stock`
--

INSERT INTO `stock` (`Id_Barang`, `Id_Toko`, `QTY_Barang`) VALUES
('SBG011', 'Tk03', '15'),
('FC01', 'Tk03', '95'),
('FC01', 'TK02', '55'),
('FC02', 'TK02', '5'),
('FC02', 'TK01', '85'),
('SBG011', 'Tk03', '5');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tb_user`
--

CREATE TABLE IF NOT EXISTS `tb_user` (
  `User_Name` varchar(30) NOT NULL,
  `Password` varchar(11) NOT NULL,
  `Group_Id` varchar(30) NOT NULL,
  `Nama` varchar(30) NOT NULL,
  `Alamat` varchar(50) NOT NULL,
  `Jenis_Kelamin` varchar(30) NOT NULL,
  `Tanggal_Masuk_Kerja` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `tb_user`
--

INSERT INTO `tb_user` (`User_Name`, `Password`, `Group_Id`, `Nama`, `Alamat`, `Jenis_Kelamin`, `Tanggal_Masuk_Kerja`) VALUES
('admin', 'admin', '0', 'Markus', 'Semarang23', 'Laki-laki', '2015-03-13'),
('kasir', 'kasir', '2', 'Willy', '', 'laki-laki', '2015-03-13'),
('owner', 'owner', '1', 'Rino', 'Ambarawa', 'Laki-laki', '2015-03-01'),
('tes', 'tes', '1', 'rino', 'senarang23', 'Laki-laki', '2015-03-13'),
('tes1', 'tes1', '1', 'rinotri', 'markus', 'Laki-laki', '2015-03-13'),
('apa', 'apa', '0', 'apa', 'Ambarawa', 'Laki-laki', '2015-03-13'),
('t', 't', '0', 'coba', 'KFC', 'Laki-laki', '2015-03-17');

-- --------------------------------------------------------

--
-- Struktur dari tabel `toko`
--

CREATE TABLE IF NOT EXISTS `toko` (
  `Id_Toko` varchar(30) NOT NULL,
  `Nama_Toko` varchar(30) NOT NULL,
  `Wilayah_Toko` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `toko`
--

INSERT INTO `toko` (`Id_Toko`, `Nama_Toko`, `Wilayah_Toko`) VALUES
('TK01', 'KFC', 'Salatiga'),
('TK02', 'innovate234', 'satiga2'),
('Tk03', 'Speedy34', 'saltiga4');

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi`
--

CREATE TABLE IF NOT EXISTS `transaksi` (
  `No_Trans` bigint(30) NOT NULL,
  `Id_Transaksi` varchar(30) NOT NULL,
  `Id_Barang` varchar(30) NOT NULL,
  `Id_Pelanggan` varchar(30) NOT NULL,
  `User_Name` varchar(30) NOT NULL,
  `Id_Toko` varchar(30) NOT NULL,
  `QTY_Barang` int(11) NOT NULL,
  `Amount` int(11) NOT NULL,
  `Tanggal` date NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `transaksi`
--

INSERT INTO `transaksi` (`No_Trans`, `Id_Transaksi`, `Id_Barang`, `Id_Pelanggan`, `User_Name`, `Id_Toko`, `QTY_Barang`, `Amount`, `Tanggal`) VALUES
(1, 'Trans1', 'FC01', 'P01', 'kasir', 'TK01', 5, 60000, '2015-03-18'),
(2, 'Trans2', 'FC01', 'P01', 'kasir', 'TK01', 9, 90000, '2015-03-18'),
(3, 'Trans3', 'FC01', 'P01', 'kasir', 'TK01', 9, 90000, '2015-03-18'),
(4, 'Trans4', 'FC02', 'P01', 'kasir', 'TK01', 5, 250000, '2015-03-18'),
(5, 'Trans5', 'FC02', 'P01', 'kasir', 'TK01', 5, 250000, '2015-03-18'),
(6, 'Trans6', 'SBG011', 'P01', 'kasir', 'TK01', 4, 40000, '2015-03-18'),
(7, 'Trans6', 'SBG012', 'P01', 'kasir', 'TK01', 6, 30000, '2015-03-18'),
(8, 'Trans6', 'FC03', 'P01', 'kasir', 'TK01', 60, 300000, '2015-03-18'),
(9, 'Trans6', 'FC02', 'P01', 'kasir', 'TK01', 1, 50000, '2015-03-18'),
(10, 'Trans7', 'FC01', 'P01', 'kasir', 'TK01', 9, 90000, '2015-03-18'),
(11, 'Trans7', 'FC02', 'P01', 'kasir', 'TK01', 9, 450000, '2015-03-18'),
(12, 'Trans8', 'FC01', 'P01', 'kasir', 'TK01', 8, 80000, '2015-03-18'),
(13, 'Trans9', 'FC03', 'P01', 'kasir', 'TK01', 8, 40000, '2015-03-18'),
(14, 'Trans10', 'SBG011', 'P01', 'kasir', 'TK01', 5, 50000, '2015-03-18'),
(15, 'Trans11', 'FC02', 'P01', 'kasir', 'TK01', 8, 400000, '2015-03-18'),
(16, 'Trans12', 'SBG012', 'P01', 'kasir', 'TK01', 8, 40000, '2015-03-18'),
(17, 'Trans13', 'SBG011', 'P01', 'kasir', 'TK01', 8, 80000, '2015-03-18'),
(18, 'Trans14', 'SBG011', 'P01', 'kasir', 'TK01', 90000, 9, '2015-03-18'),
(19, 'Trans15', 'SBG011', 'P01', 'kasir', 'TK01', 9, 90000, '2015-03-18'),
(20, 'Trans15', 'FC02', 'P01', 'kasir', 'TK01', 5, 250000, '2015-03-18'),
(21, 'Trans16', 'FC01', 'P01', 'kasir', 'TK01', 8, 80000, '2015-03-18'),
(22, 'Trans17', 'FC03', 'P01', 'kasir', 'Item 1', 3, 15000, '2015-03-18'),
(23, 'Trans18', 'FC03', 'P01', 'kasir', 'KFC', 2, 10000, '2015-03-21'),
(24, 'Trans18', 'SBG012', 'P01', 'kasir', 'KFC', 1, 5000, '2015-03-21'),
(25, 'Trans19', 'SBG012', '0', 'kasir', 'KFC', 2, 10000, '2015-03-21'),
(26, 'Trans20', 'FC03', 'P01', 'kasir', 'KFC', 1, 5000, '2015-03-21'),
(27, 'Trans21', 'FC03', '0', 'kasir', 'KFC', 1, 5000, '2015-03-21'),
(28, 'Trans22', 'FC03', '0', 'kasir', 'KFC', 3, 15000, '2015-03-21'),
(29, 'Trans23', 'FC02', 'P01', 'kasir', 'KFC', 3, 150000, '2015-03-21'),
(30, 'Trans24', 'FC01', '0', 'kasir', 'KFC', 1, 10000, '2015-03-21'),
(31, 'Trans25', 'FC02', 'P02', 'kasir', 'KFC', 5, 250000, '2015-03-21'),
(32, 'Trans25', 'FC02', 'P02', 'kasir', 'KFC', 5, 250000, '2015-03-21'),
(33, 'Trans25', 'SBG011', 'P02', 'kasir', 'KFC', 9, 90000, '2015-03-21'),
(34, 'Trans26', 'SBG011', '0', 'kasir', 'KFC', 6, 60000, '2015-03-21'),
(35, 'Trans26', 'SBG012', '0', 'kasir', 'KFC', 4, 20000, '2015-03-21'),
(36, 'Trans27', 'FC01', '0', 'kasir', 'Speedy34', 4, 40000, '2015-03-21'),
(37, 'Trans27', 'FC02', '0', 'kasir', 'Speedy34', 6, 300000, '2015-03-21'),
(38, 'Trans28', 'SBG011', '0', 'kasir', 'KFC', 6, 60000, '2015-03-21'),
(39, 'Trans29', 'FC01', 'P01', 'kasir', 'KFC', 6, 60000, '2015-03-21'),
(40, 'Trans30', 'SBG011', 'P06', 'kasir', 'KFC', 8, 80000, '2015-03-21'),
(41, 'Trans30', 'FC01', 'P06', 'kasir', 'KFC', 6, 60000, '2015-03-21');

-- --------------------------------------------------------

--
-- Struktur dari tabel `utang`
--

CREATE TABLE IF NOT EXISTS `utang` (
  `Id_Utang` varchar(30) NOT NULL,
  `Id_Pelanggan` varchar(30) NOT NULL,
  `Id_Transaksi` varchar(30) NOT NULL,
  `Cicilan` int(11) NOT NULL,
  `Total_Harga_Transaksi` int(11) NOT NULL,
  `Sisa_kali_cicilan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data untuk tabel `utang`
--

INSERT INTO `utang` (`Id_Utang`, `Id_Pelanggan`, `Id_Transaksi`, `Cicilan`, `Total_Harga_Transaksi`, `Sisa_kali_cicilan`) VALUES
('Utg1', 'P01', 'Trans18', 5, 0, 4),
('Utg2', 'P01', 'Trans20', 5, 0, 5),
('Utg3', 'P01', 'Trans23', 5, 117000, 4),
('Utg4', 'P02', 'Trans25', 5, 90000, 5),
('Utg5', 'P03', 'Trans27', 5, -900, 4),
('Utg6', 'P01', 'Trans29', 5, 54000, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
 ADD PRIMARY KEY (`Id_Barang`);

--
-- Indexes for table `group_barang`
--
ALTER TABLE `group_barang`
 ADD PRIMARY KEY (`Group_Id`);

--
-- Indexes for table `group_user`
--
ALTER TABLE `group_user`
 ADD PRIMARY KEY (`Group_Id`);

--
-- Indexes for table `pelanggan`
--
ALTER TABLE `pelanggan`
 ADD PRIMARY KEY (`Id_Pelanggan`);

--
-- Indexes for table `tb_user`
--
ALTER TABLE `tb_user`
 ADD PRIMARY KEY (`User_Name`);

--
-- Indexes for table `toko`
--
ALTER TABLE `toko`
 ADD PRIMARY KEY (`Id_Toko`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
 ADD PRIMARY KEY (`No_Trans`,`Id_Transaksi`);

--
-- Indexes for table `utang`
--
ALTER TABLE `utang`
 ADD PRIMARY KEY (`Id_Utang`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
