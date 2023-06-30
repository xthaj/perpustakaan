package com.uas.pbo.controller;

import com.uas.pbo.chatbot.QuestionMessage;
import com.uas.pbo.chatbot.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class FAQBotController {

    @MessageMapping("/question")
    @SendTo("/topic/responses")
    public ResponseMessage answer(QuestionMessage question) throws InterruptedException {
        String response = getResponseForQuestion(question.getQuestion());
        return new ResponseMessage(response);
    }

    private String getResponseForQuestion(String question) {
        if (question.contains("Bagaimana cara pengembalian buku?")) { //7
            return "Pengembalian hanya bisa dilakukan oleh pustakawan.";
        } else if (question.contains("Mengapa saya tidak bisa meminjam buku?")) { //3
            return "Semua eksemplar sedang dipinjam atau kami tidak memiliki buku fisik.";
        } else if (question.contains("Apa saja kategori buku yang tersedia?")) { //3
            return "Genre buku dapat dilihat di tab Eksplorasi Buku."; //5
        } else if (question.contains("Bagaimana saya bisa tahu jika buku dalam waitlist sudah tersedia?")) { //8
            return "Akan muncul notifikasi di dashboard, harap menunggu.";
        } else if (question.contains("Bagaimana cara melihat buku yang tersedia untuk dipinjam?")) {
            return "Lihat eksplorasi buku, Status Eksemplar. Jika ada eksemplar yang tersedia, buku akan bisa dipinjam."; //6
        } else if (question.contains("Berapa lama durasi peminjaman?") || question.contains("Bagaimana memperpanjang masa peminjaman?")) { //1
            return "Silakan konsultasikan pada pustakawan.";
        } else if (question.contains("Bagaimana cara melakukan peminjaman?")) { //2
            return "Lihat tab eksplorasi buku dan klik pinjam.";
        } else if (question.contains("Bagaimana cara keluar dari akun saya?")) { //2
            return "Tombol logout ada di pojok kanan atas.";
        } else if (question.contains("Di dashboard ada notifikasi, tetapi saya tidak bisa meminjam buku")) {
            return "Buku sempat tersedia, namun sudah ada yang meminjam. Anda dapat masuk kembali ke dalam waitlist.";
        } else if (containsKeyword(question, "peminjaman") || containsKeyword(question, "pinjam")) {
            return "Saya kurang mengerti pertanyaan kamu, berikut contoh pertanyaan tentang peminjaman:<br>"
                    + "1. Berapa lama durasi peminjaman?<br>" //1
                    + "2. Bagaimana cara melakukan peminjaman?<br>" //2
                    + "3. Mengapa saya tidak bisa meminjam buku?<br>" //3
                    + "4. Bagaimana memperpanjang masa peminjaman?"; //4
        } else if (containsKeyword(question, "buku")) {
            return "Saya kurang mengerti pertanyaan kamu, berikut contoh pertanyaan tentang buku:<br>"
                    + "1. Apa saja kategori buku yang tersedia?<br>" //5
                    + "2. Bagaimana cara melihat buku yang tersedia untuk dipinjam?<br>"; //6
        } else if (containsKeyword(question, "kembali") || containsKeyword(question, "pengembalian")) {
            return "Saya kurang mengerti pertanyaan kamu, berikut contoh pertanyaan tentang pengembalian buku:<br>"
                    + "1. Bagaimana cara pengembalian buku?<br>"; //7
        } else if (containsKeyword(question, "waitlist") || containsKeyword(question, "daftar tunggu")) {
            return "Saya kurang mengerti pertanyaan kamu, berikut contoh tentang waitlist:<br>"
                    + "1. Di dashboard ada notifikasi, tetapi saya tidak bisa meminjam buku<br>"
                    + "2. Bagaimana saya bisa tahu jika buku dalam waitlist sudah tersedia?<br>"; //8
        } else if (containsKeyword(question, "akun")) {
            return "Saya kurang mengerti pertanyaan kamu, berikut contoh pertanyaan tentang akun:<br>"
                    + "1. Bagaimana cara keluar dari akun saya?<br>"; //10
        } else {
            return "Maaf, pertanyaan tidak dikenali. Silakan coba pertanyaan lain. Coba keyword = buku, pinjam, kembali, akun, atau waitlist";
        }
    }

    private boolean containsKeyword(String question, String keyword) {
        return question.toLowerCase().contains(keyword.toLowerCase());
    }

}
