package org.delcom.starter.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Unit Test untuk HomeController")
class HomeControllerUnitTest {

    private String toBase64(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    @Nested
    @DisplayName("Metode Dasar")
    class BasicMethods {
        @Test
        @DisplayName("Mengembalikan pesan selamat datang yang benar")
        void hello_ShouldReturnWelcomeMessage() {
            // Arrange
            HomeController controller = new HomeController();
            // Act
            String result = controller.hello();
            // Assert
            assertEquals("Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!", result);
        }

        @Test
        @DisplayName("Mengembalikan pesan sapaan yang dipersonalisasi")
        void helloWithName_ShouldReturnPersonalizedGreeting() {
            // Arrange
            HomeController controller = new HomeController();
            // Act
            String result = controller.sayHello("Abdullah");
            // Assert
            assertEquals("Hello, Abdullah!", result);
        }
    }

    @Nested
    @DisplayName("Praktikum 1: Informasi NIM")
    class InformasiNim {
        @Test
        @DisplayName("NIM Valid")
        void nimValid() {
            HomeController c = new HomeController();
            String r = c.informasiNim("11S24001");
            assertTrue(r.contains("Sarjana Informatika") && r.contains("Urutan: 1"));
        }

        @Test
        @DisplayName("NIM Sarjana Sistem Informasi")
        void nimSI() {
            HomeController c = new HomeController();
            String r = c.informasiNim("12S24001");
            assertTrue(r.contains("Sarjana Sistem Informasi"));
        }

        @Test
        @DisplayName("NIM D3 Teknologi Informasi")
        void nimD3TI() {
            HomeController c = new HomeController();
            String r = c.informasiNim("11324001");
            assertTrue(r.contains("Diploma 3 Teknologi Informasi"));
        }

        @Test
        @DisplayName("NIM D4 TRPL")
        void nimD4() {
            HomeController c = new HomeController();
            String r = c.informasiNim("11424012");
            assertTrue(r.contains("Diploma 4 Teknologi Rekayasa Perangkat Lunak"));
        }

        @Test
        @DisplayName("NIM Tidak Dikenal")
        void nimInvalid() {
            HomeController c = new HomeController();
            String r = c.informasiNim("99X99999");
            assertTrue(r.contains("Program studi tidak dikenal"));
        }

        @Test
        @DisplayName("NIM Prodi Selain Di atas")
        void otherProdi() {
            HomeController c = new HomeController();
            assertTrue(c.informasiNim("14S24001").contains("Sarjana Teknik Elektro"));
            assertTrue(c.informasiNim("21S24001").contains("Sarjana Manajemen Rekayasa"));
            assertTrue(c.informasiNim("22S24001").contains("Sarjana Teknik Metalurgi"));
            assertTrue(c.informasiNim("31S24001").contains("Sarjana Teknik Bioproses"));
            assertTrue(c.informasiNim("13324001").contains("Diploma 3 Teknologi Komputer"));
        }
    }

    @Nested
    @DisplayName("Praktikum 2: Perolehan Nilai")
    class PerolehanNilai {
        @Test
        @DisplayName("Input Valid Sesuai Logika Asli")
        void validInput() {
            HomeController c = new HomeController();
            String input = "10\n20\n10\n10\n20\n30\nT|90|21\nUAS|92|82\nPA|75|45\n---";
            String result = c.perolehanNilai(toBase64(input));
            assertTrue(result.contains(">> Nilai Akhir: 37.30"));
            assertTrue(result.contains(">> Grade: D"));
        }

        @Test
        @DisplayName("Input Komprehensif untuk Coverage")
        void comprehensiveInputForCoverage() {
            HomeController c = new HomeController();
            String input = "5\n5\n5\n5\n40\n40\nPA|100|85\nT|100|90\n\nK|100|80\nP|100|75\nUTS|100|70\nUAS-salah\nUAS|100|bukan-angka\nXYZ|10|10\n---";
            String result = c.perolehanNilai(toBase64(input));
            assertTrue(result.contains(">> Nilai Akhir: 44.50"));
            assertTrue(result.contains(">> Grade: D"));
        }

        @Test
        @DisplayName("Input dengan Nilai Max 0")
        void inputWithMaxZero() {
            HomeController c = new HomeController();
            String input = "10\n90\n0\n0\n0\n0\nPA|100|80\nT|0|90\n---";
            String result = c.perolehanNilai(toBase64(input));
            assertTrue(result.contains(">> Partisipatif: 80/100"));
            assertTrue(result.contains(">> Tugas: 0/100"));
        }

        @Test
        @DisplayName("Input Tidak Valid")
        void invalidInput() {
            HomeController c = new HomeController();
            String r = c.perolehanNilai("input-salah-base64-@!#");
            assertTrue(r.contains("Error"));
        }

        @Test
        @DisplayName("Mencakup semua Grade Nilai")
        void allGrades() {
            HomeController c = new HomeController();
            String bobot = "0\n0\n0\n0\n0\n100\n";

            String inputA = bobot + "UAS|100|85\n---";
            assertTrue(c.perolehanNilai(toBase64(inputA)).contains(">> Grade: A"));

            String inputAB = bobot + "UAS|100|75\n---";
            assertTrue(c.perolehanNilai(toBase64(inputAB)).contains(">> Grade: AB"));

            String inputB = bobot + "UAS|100|70\n---";
            assertTrue(c.perolehanNilai(toBase64(inputB)).contains(">> Grade: B"));

            String inputBC = bobot + "UAS|100|60\n---";
            assertTrue(c.perolehanNilai(toBase64(inputBC)).contains(">> Grade: BC"));

            String inputC = bobot + "UAS|100|50\n---";
            assertTrue(c.perolehanNilai(toBase64(inputC)).contains(">> Grade: C"));
        }
    }

    @Nested
    @DisplayName("Praktikum 3: Perbedaan L")
    class PerbedaanL {
        @Test
        @DisplayName("Matriks Ganjil Valid")
        void validOddMatrix() {
            HomeController c = new HomeController();
            String i = "3\n1 2 3\n4 5 6\n7 8 9";
            String r = c.perbedaanL(toBase64(i));
            assertTrue(r.contains("Nilai L: 29") && r.contains("Nilai Kebalikan L: 21"));
        }

        @Test
        @DisplayName("Matriks Genap Valid")
        void validEvenMatrix() {
            HomeController c = new HomeController();
            String i = "4\n1 1 1 1\n2 2 2 2\n3 3 3 3\n4 4 4 4";
            String r = c.perbedaanL(toBase64(i));
            assertTrue(r.contains("Nilai Tengah: 10"));
        }

        @Test
        @DisplayName("Matriks < 3x3")
        void smallMatrix() {
            HomeController c = new HomeController();
            String i = "2\n1 2\n3 4";
            String r = c.perbedaanL(toBase64(i));
            assertTrue(r.contains("Nilai L: Tidak Ada") && r.contains("Dominan: 10"));
        }

        @Test
        @DisplayName("Perbedaan 0")
        void zeroDifference() {
            HomeController c = new HomeController();
            String i = "3\n10 1 10\n1 1 1\n10 1 10";
            String r = c.perbedaanL(toBase64(i));
            assertTrue(r.contains("Perbedaan: 0") && r.contains("Dominan: 1"));
        }

        @Test
        @DisplayName("Input Matriks Tidak Valid")
        void invalidMatrixInput() {
            HomeController c = new HomeController();
            String i = "3\n1 2 3\n4 x 6\n7 8 9";
            String r = c.perbedaanL(toBase64(i));
            assertTrue(r.contains("Error: Input tidak valid."));
        }
    }

    @Nested
    @DisplayName("Praktikum 4: Paling Ter")
    class PalingTer {
        @Test
        @DisplayName("Input Valid Sesuai Logika Asli")
        void validInput() {
            HomeController c = new HomeController();
            String input = "10\n5\n10\n20\n5\n10\n---";
            String result = c.palingTer(toBase64(input));
            assertTrue(result.contains("Jumlah Tertinggi: 10 * 3 = 30"));
            assertTrue(result.contains("Jumlah Terendah: 5 * 2 = 10"));
        }

        @Test
        @DisplayName("Kasus Tie-breaker")
        void tieBreaker() {
            HomeController c = new HomeController();
            String input = "6\n6\n4\n4\n4\n10\n10\n5\n5\n5\n5\n---";
            String result = c.palingTer(toBase64(input));
            assertTrue(result.contains("Tersedikit: 6 (2x)"));
            assertTrue(result.contains("Jumlah Tertinggi: 10 * 2 = 20"));
            assertTrue(result.contains("Jumlah Terendah: 4 * 3 = 12"));
        }

        @Test
        @DisplayName("Input Kosong (hanya ---)")
        void emptyInputOnlyDelimiter() {
            HomeController c = new HomeController();
            String r = c.palingTer(toBase64("---\n"));
            assertTrue(r.contains("Error: Tidak ada data input."));
        }

        @Test
        @DisplayName("Input Kosong (string kosong)")
        void emptyInput() {
            HomeController c = new HomeController();
            String r = c.palingTer(toBase64(""));
            assertTrue(r.contains("Error: Tidak ada data input."));
        }

        @Test
        @DisplayName("Input dengan Data Non-Numerik")
        void invalidNonNumericInput() {
            HomeController c = new HomeController();
            String r = c.palingTer(toBase64("10\nhello\n20\n---"));
            assertTrue(r.contains("Error: Input tidak valid."));
        }

        @Test
        @DisplayName("Input dengan Satu Angka Unik")
        void singleUniqueNumberInput() {
            HomeController c = new HomeController();
            String input = "7\n7\n7\n---";
            String result = c.palingTer(toBase64(input));
            assertTrue(result.contains("Tertinggi: 7"));
            assertTrue(result.contains("Terendah: 7"));
            assertTrue(result.contains("Terbanyak: 7 (3x)"));
            assertTrue(result.contains("Tersedikit: 7 (3x)"));
            assertTrue(result.contains("Jumlah Tertinggi: 7 * 3 = 21"));
            assertTrue(result.contains("Jumlah Terendah: 7 * 3 = 21"));
        }

        @Test
        @DisplayName("Tie-breaker Jumlah Terendah")
        void tieBreakerJumlahTerendah() {
            HomeController c = new HomeController();
            String input = "10\n10\n4\n4\n4\n4\n4\n---";
            String result = c.palingTer(toBase64(input));
            assertTrue(result.contains("Jumlah Terendah: 4 * 5 = 20"));
        }

        @Test
        @DisplayName("Tie-breaker Jumlah Tertinggi")
        void tieBreakerJumlahTertinggi() {
            HomeController c = new HomeController();
            String input = "6\n6\n6\n6\n6\n10\n10\n10\n---";
            String expectedOutput = "Tertinggi: 10\n" + 
                    "Terendah: 6\n" + 
                    "Terbanyak: 6 (5x)\n" + 
                    "Tersedikit: 10 (3x)\n" + 
                    "Jumlah Tertinggi: 10 * 3 = 30\n" + 
                    "Jumlah Terendah: 6 * 5 = 30";
            String actualOutput = c.palingTer(toBase64(input));
            assertEquals(expectedOutput, actualOutput);
        }
    }
}