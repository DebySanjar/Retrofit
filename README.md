# 📋 ToDo App – Retrofit bilan Android ilovasi

Bu Android ilovasi `Retrofit` kutubxonasi yordamida REST API bilan ishlovchi oddiy ToDo ilovasidir. Ilova orqali foydalanuvchi vazifalarni ko‘rishi, yangi vazifa qo‘shishi, o‘zgartirishi va o‘chirish imkoniyatiga ega.

## 🚀 Texnologiyalar

- Kotlin
- Retrofit
- Gson Converter
- RecyclerView
- ViewBinding
- Material Design

## 📱 Ilova funksiyalari

- 📥 Vazifalar ro‘yxatini API’dan olish (`GET`)
- ➕ Yangi vazifa qo‘shish (`POST`)
- ✏️ Vazifani tahrirlash (`PUT`)
- ❌ Vazifani o‘chirish (`DELETE`)
- ♻️ Ma’lumotlarni avtomatik yangilash

## 🛠 API bilan ulanish

Ilova `Retrofit` orqali quyidagi endpoint’lar bilan ishlaydi:

| Amal | Endpoint | Tavsif |
|------|----------|--------|
| GET | `rejalar/` | Barcha vazifalarni olish |
| POST | `rejalar/` | Yangi vazifa yaratish |
| PUT | `/rejalar/{id}/` | Vazifani tahrirlash |
| DELETE | `/rejalar/{id}/` | Vazifani o‘chirish |
