package com.example.food_xyz_v1.model

data class Barang (
    public val idBarang : String,
//  public val kodeBarang: String,
    public val namaBarang: String,
    public val jumlahBarang: String,
//  public val satuan: String,
    public val hargaSatuan: String,
    public var jumlahBarangDibeli: Int
)