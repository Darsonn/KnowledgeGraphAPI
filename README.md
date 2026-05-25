# Knowledge Graph API

[![Java](https://img.shields.io/badge/Java-25-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Supported-blue.svg)](https://www.postgresql.org/)
[![JWT](https://img.shields.io/badge/Security-JWT-black.svg)](https://jwt.io/)

Zaawansowane REST API zbudowane w oparciu o najnowsze standardy ekosystemu Spring Boot. Aplikacja służy jako backend do zarządzania zasobami grafu wiedzy (węzły informacji i tagi), zapewniając wysoki poziom bezpieczeństwa dzięki bezstanowej autoryzacji.

## 🚀 Główne funkcjonalności

- **Zaawansowana autoryzacja (JWT):** Implementacja Spring Security z wykorzystaniem JSON Web Tokens. Obsługa cyklu życia tokenów, w tym generowanie `Access Token` oraz bezpieczny mechanizm `Refresh Token`.
- **Zarządzanie wiedzą (CRUD):** Pełna obsługa encji bazodanowych (Memories, Tags) powiązanych z kontem konkretnego, zalogowanego użytkownika.
- **Relacyjna baza danych:** Wykorzystanie PostgreSQL wraz ze Spring Data JPA / Hibernate do modelowania powiązań między użytkownikami, wspomnieniami i tagami.
- **Globalna obsługa błędów:** Wdrożony `GlobalExceptionHandler` zapewniający spójne i czytelne odpowiedzi błędów (ErrorResponse) we wszystkich endpointach API (np. dla ResourceNotFoundException, DuplicateException).
- **Dockeryzacja:** Projekt przygotowany do łatwego wdrożenia dzięki dołączonemu plikowi `Dockerfile`.

## 🛠️ Stack technologiczny

- **Java 25**
- **Spring Boot 4.0.2** (Web, Data JPA, Security)
- **PostgreSQL** (baza danych)
- **JJWT (Java JWT)** (wersja 0.11.5)
- **Lombok**
- **Maven** (budowanie projektu)

## ⚙️ Wymagania i uruchomienie

Aby uruchomić aplikację lokalnie, musisz skonfigurować środowisko:

1. **Sklonuj repozytorium**
   ```bash
   git clone https://github.com/Darsonn/KnowledgeGraphAPI.git
   cd KnowledgeGraphAPI
   ```

2. **Zmienne środowiskowe**
   Aplikacja wymaga ustawienia następujących zmiennych w środowisku lub bezpośrednio w pliku `application.properties`:
   - `DB_URL` - URL do bazy PostgreSQL
   - `DB_USER` / `DB_PASSWORD` - dane logowania do bazy
   - `JWT_TOKEN` - silny klucz sekretny (HMAC) do podpisywania tokenów
   - `JWT_EXPIRATION` / `JWT_REFRESH_TOKEN_EXPIRATION` - czas wygasania tokenów w milisekundach

3. **Uruchomienie za pomocą Maven Wrapper**
   Aplikacja korzysta z wbudowanego wrappera Mavena, więc nie musisz mieć zainstalowanego Mavena globalnie:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📖 Endpointy API

Aplikacja udostępnia m.in. następujące kluczowe zbiory endpointów:
- `POST /auth/register` | `POST /auth/login` | `POST /auth/refresh-token` - zarządzanie sesją użytkownika.
- `GET /health` - publiczny endpoint weryfikujący stan aplikacji.
- `GET /memories` | `POST /memories` | `PUT /memories/{id}` | `DELETE /memories/{id}` - autoryzowane zarządzanie zasobami (Memories).
- Podobne operacje dla modułu Tagów i Konta Użytkownika.
