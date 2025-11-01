package org.delcom.starter.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class HomeController {

    // Metode Dasar
    @GetMapping("/")
    public String hello() {
        return "Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!";
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    // Endpoint Praktikum 1
    @GetMapping("/informasiNim/{nim}")
    public String informasiNim(@PathVariable String nim) {
        String prefix = nim.substring(0, 3);
        String angkatan = nim.substring(3, 5);
        String nomor = nim.substring(5);
        String prodi;
        switch (prefix) {
            case "11S":
                prodi = "Sarjana Informatika";
                break;
            case "12S":
                prodi = "Sarjana Sistem Informasi";
                break;
            case "14S":
                prodi = "Sarjana Teknik Elektro";
                break;
            case "21S":
                prodi = "Sarjana Manajemen Rekayasa";
                break;
            case "22S":
                prodi = "Sarjana Teknik Metalurgi";
                break;
            case "31S":
                prodi = "Sarjana Teknik Bioproses";
                break;
            case "114":
                prodi = "Diploma 4 Teknologi Rekayasa Perangkat Lunak";
                break;
            case "113":
                prodi = "Diploma 3 Teknologi Informasi";
                break;
            case "133":
                prodi = "Diploma 3 Teknologi Komputer";
                break;
            default:
                prodi = "Program studi tidak dikenal";
        }
        nomor = nomor.replaceFirst("^0+", "");
        return String.format("Inforamsi NIM %s: \n>> Program Studi: %s\n>> Angkatan: 20%s\n>> Urutan: %s", nim, prodi,
                angkatan, nomor);
    }

    // Endpoint Praktikum 2
    @GetMapping("/perolehanNilai/{strBase64}")
    public String perolehanNilai(@PathVariable String strBase64) {
        try {
            String decodedInput = new String(Base64.getDecoder().decode(strBase64));
            String[] lines = decodedInput.split("\\r?\\n");
            int i = 0;
            int bobotPA = Integer.parseInt(lines[i++].trim());
            int bobotT = Integer.parseInt(lines[i++].trim());
            int bobotK = Integer.parseInt(lines[i++].trim());
            int bobotP = Integer.parseInt(lines[i++].trim());
            int bobotUTS = Integer.parseInt(lines[i++].trim());
            int bobotUAS = Integer.parseInt(lines[i++].trim());
            double pPA = 0, pT = 0, pK = 0, pP = 0, pUTS = 0, pUAS = 0;
            double mPA = 0, mT = 0, mK = 0, mP = 0, mUTS = 0, mUAS = 0;
            for (; i < lines.length; i++) {
                String l = lines[i].trim();
                if (l.equals("---"))
                    break;
                if (l.isEmpty())
                    continue;
                String[] p = l.split("\\|");
                if (p.length != 3)
                    continue;
                try {
                    String j = p[0].trim();
                    int nm = Integer.parseInt(p[1].trim());
                    int np = Integer.parseInt(p[2].trim());
                    switch (j) {
                        case "PA":
                            pPA += np;
                            mPA += nm;
                            break;
                        case "T":
                            pT += np;
                            mT += nm;
                            break;
                        case "K":
                            pK += np;
                            mK += nm;
                            break;
                        case "P":
                            pP += np;
                            mP += nm;
                            break;
                        case "UTS":
                            pUTS += np;
                            mUTS += nm;
                            break;
                        case "UAS":
                            pUAS += np;
                            mUAS += nm;
                            break;
                    }
                } catch (NumberFormatException e) {
                    continue;
                }
            }
            class H {
                int pf(double p, double m) {
                    if (m <= 0)
                        return 0;
                    return (int) Math.floor((p / m) * 100.0 + 1e-9);
                }

                double c(int pr, int b) {
                    return (pr / 100.0) * b;
                }

                String g(double n) {
                    if (n >= 79.5)
                        return "A";
                    else if (n >= 72)
                        return "AB";
                    else if (n >= 64.5)
                        return "B";
                    else if (n >= 57)
                        return "BC";
                    else if (n >= 49.5)
                        return "C";
                    else if (n >= 34)
                        return "D";
                    else
                        return "E";
                }
            }
            H h = new H();
            int pp = h.pf(pPA, mPA);
            int pt = h.pf(pT, mT);
            int pk = h.pf(pK, mK);
            int pp_proj = h.pf(pP, mP);
            int puts = h.pf(pUTS, mUTS);
            int puas = h.pf(pUAS, mUAS);
            double na = h.c(pp, bobotPA) + h.c(pt, bobotT) + h.c(pk, bobotK) + h.c(pp_proj, bobotP)
                    + h.c(puts, bobotUTS) + h.c(puas, bobotUAS);
            return "Perolehan Nilai:\n" +
                    String.format(Locale.US, ">> Partisipatif: %d/100 (%.2f/%d)\n", pp, h.c(pp, bobotPA), bobotPA) +
                    String.format(Locale.US, ">> Tugas: %d/100 (%.2f/%d)\n", pt, h.c(pt, bobotT), bobotT) +
                    String.format(Locale.US, ">> Kuis: %d/100 (%.2f/%d)\n", pk, h.c(pk, bobotK), bobotK) +
                    String.format(Locale.US, ">> Proyek: %d/100 (%.2f/%d)\n", pp_proj, h.c(pp_proj, bobotP), bobotP) +
                    String.format(Locale.US, ">> UTS: %d/100 (%.2f/%d)\n", puts, h.c(puts, bobotUTS), bobotUTS) +
                    String.format(Locale.US, ">> UAS: %d/100 (%.2f/%d)\n", puas, h.c(puas, bobotUAS), bobotUAS) +
                    String.format(Locale.US, "\n>> Nilai Akhir: %.2f\n", na) + ">> Grade: " + h.g(na);
        } catch (Exception e) {
            return "Error: Input tidak valid.";
        }
    }

    // Endpoint Praktikum 3
    @GetMapping("/perbedaanL/{strBase64}")
    public String perbedaanL(@PathVariable String strBase64) {
        try {
            String decodedInput = new String(Base64.getDecoder().decode(strBase64));
            String[] lines = decodedInput.split("\\r?\\n");
            int n = Integer.parseInt(lines[0].trim());
            int[][] matrix = new int[n][n];
            for (int i = 0; i < n; i++) {
                String[] rowValues = lines[i + 1].trim().split("\\s+");
                for (int j = 0; j < n; j++) {
                    matrix[i][j] = Integer.parseInt(rowValues[j]);
                }
            }
            int nilaiL = -1, nilaiKebalikanL = -1;
            if (n >= 3) {
                nilaiL = 0;
                for (int i = 0; i < n; i++)
                    nilaiL += matrix[i][0];
                for (int j = 0; j < n; j++)
                    nilaiL += matrix[n - 1][j];
                nilaiL -= matrix[n - 1][0];
                nilaiKebalikanL = 0;
                for (int j = 0; j < n; j++)
                    nilaiKebalikanL += matrix[0][j];
                for (int i = 0; i < n; i++)
                    nilaiKebalikanL += matrix[i][n - 1];
                nilaiKebalikanL -= matrix[0][n - 1];
            }
            int nilaiTengah;
            if (n % 2 == 1) {
                nilaiTengah = matrix[n / 2][n / 2];
            } else {
                int m1 = n / 2 - 1, m2 = n / 2;
                nilaiTengah = matrix[m1][m1] + matrix[m1][m2] + matrix[m2][m1] + matrix[m2][m2];
            }
            String pStr;
            int p = 0;
            if (nilaiL == -1) {
                pStr = "Tidak Ada";
            } else {
                p = Math.abs(nilaiL - nilaiKebalikanL);
                pStr = String.valueOf(p);
            }
            int dominan;
            if (nilaiL == -1 || p == 0) {
                dominan = nilaiTengah;
            } else {
                dominan = Math.max(nilaiL, nilaiKebalikanL);
            }
            return String.format("Nilai L: %s\nNilai Kebalikan L: %s\nNilai Tengah: %d\nPerbedaan: %s\nDominan: %d",
                    (nilaiL == -1 ? "Tidak Ada" : nilaiL), (nilaiKebalikanL == -1 ? "Tidak Ada" : nilaiKebalikanL),
                    nilaiTengah, pStr, dominan);
        } catch (Exception e) {
            return "Error: Input tidak valid.";
        }
    }

    // Endpoint Praktikum 4
    @GetMapping("/palingTer/{strBase64}")
    public String palingTer(@PathVariable String strBase64) {
        try {
            String decodedInput = new String(Base64.getDecoder().decode(strBase64));
            ArrayList<Integer> listNilai = new ArrayList<>();
            String[] lines = decodedInput.split("\\r?\\n");
            for (String line : lines) {
                String t = line.trim();
                if (t.equals("---"))
                    break;
                if (t.isEmpty())
                    continue;
                listNilai.add(Integer.parseInt(t));
            }
            if (listNilai.isEmpty())
                return "Error: Tidak ada data input.";

            HashMap<Integer, Integer> freqMap = new LinkedHashMap<>();
            for (int val : listNilai) {
                freqMap.put(val, freqMap.getOrDefault(val, 0) + 1);
            }

            int maxVal = Collections.max(listNilai);
            int minVal = Collections.min(listNilai);

            int frekTerbanyak = 0;
            for (int freq : freqMap.values()) {
                if (freq > frekTerbanyak)
                    frekTerbanyak = freq;
            }
            int angkaTerbanyak = 0;
            for (int val : listNilai) {
                if (freqMap.get(val) == frekTerbanyak) {
                    angkaTerbanyak = val;
                    break;
                }
            }

            int frekTersedikit = Integer.MAX_VALUE;
            for (int freq : freqMap.values()) {
                if (freq < frekTersedikit)
                    frekTersedikit = freq;
            }
            int angkaTersedikit = 0;
            for (int val : listNilai) {
                if (freqMap.get(val) == frekTersedikit) {
                    angkaTersedikit = val;
                    break;
                }
            }

            int nilaiJumlahTertinggi = 0, jumlahTertinggi = -1, frekJumlahTertinggi = 0;
            for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
                int angka = entry.getKey();
                int freq = entry.getValue();
                int total = angka * freq;
                if (total > jumlahTertinggi) {
                    jumlahTertinggi = total;
                    nilaiJumlahTertinggi = angka;
                    frekJumlahTertinggi = freq;
                } else if (total == jumlahTertinggi) {
                    if (angka > nilaiJumlahTertinggi) {
                        nilaiJumlahTertinggi = angka;
                        frekJumlahTertinggi = freq;
                    }
                }
            }

            int nilaiJumlahTerendah = 0, jumlahTerendah = Integer.MAX_VALUE;
            for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
                int angka = entry.getKey();
                int total = angka * entry.getValue();
                if (total < jumlahTerendah) {
                    jumlahTerendah = total;
                    nilaiJumlahTerendah = angka;
                } else if (total == jumlahTerendah) {
                    if (angka < nilaiJumlahTerendah) {
                        nilaiJumlahTerendah = angka;
                    }
                }
            }

            return String.format(
                    "Tertinggi: %d\nTerendah: %d\nTerbanyak: %d (%dx)\nTersedikit: %d (%dx)\nJumlah Tertinggi: %d * %d = %d\nJumlah Terendah: %d * %d = %d",
                    maxVal, minVal, angkaTerbanyak, frekTerbanyak, angkaTersedikit, frekTersedikit,
                    nilaiJumlahTertinggi, frekJumlahTertinggi, jumlahTertinggi,
                    nilaiJumlahTerendah, freqMap.get(nilaiJumlahTerendah), jumlahTerendah);

        } catch (Exception e) {
            return "Error: Input tidak valid.";
        }
    }
}