import requests
import time
import json
import os

BASE_URL = "http://localhost:8080"

# Colors for output
class Colors:
    GREEN = '\033[92m'
    RED = '\033[91m'
    RESET = '\033[0m'

def print_result(name, res):
    if res.status_code in [200, 201]:
        print(f"{Colors.GREEN}[SUCCESS]{Colors.RESET} {name} (Status: {res.status_code})")
    else:
        print(f"{Colors.RED}[FAILED]{Colors.RESET} {name} (Status: {res.status_code}) - {res.text}")

def run_tests():
    print("--- Starting Letit API Tests ---")

    # 1. Register User 1
    u1 = {"userName": "alice", "email": "alice@test.com", "password": "password123"}
    res = requests.post(f"{BASE_URL}/auth/register", json=u1)
    print_result("Register User 1", res)

    # 2. Register User 2
    u2 = {"userName": "bob", "email": "bob@test.com", "password": "password123"}
    res = requests.post(f"{BASE_URL}/auth/register", json=u2)
    print_result("Register User 2", res)

    # 3. Login User 1
    res = requests.post(f"{BASE_URL}/auth/login", params={"email": u1["email"], "password": u1["password"]})
    print_result("Login User 1", res)
    token1 = res.json().get("token") if res.status_code == 200 else ""
    headers1 = {"Authorization": f"Bearer {token1}"}

    # 4. Login User 2
    res = requests.post(f"{BASE_URL}/auth/login", params={"email": u2["email"], "password": u2["password"]})
    print_result("Login User 2", res)
    token2 = res.json().get("token") if res.status_code == 200 else ""
    headers2 = {"Authorization": f"Bearer {token2}"}

    # Create dummy file for post upload
    with open("dummy.jpg", "wb") as f:
        f.write(b"dummy image content")

    # 5. Create Post (User 1)
    with open("dummy.jpg", "rb") as f:
        res = requests.post(
            f"{BASE_URL}/post", 
            headers={"Authorization": f"Bearer {token1}"},
            data={"caption": "Hello world from Alice!"},
            files={"file": ("dummy.jpg", f, "image/jpeg")}
        )
    print_result("Create Post (User 1)", res)
    
    # Wait slightly to ensure post is committed
    time.sleep(1)

    # 6. Get User 1's posts to find postId
    res = requests.get(f"{BASE_URL}/post/user/alice", headers=headers1)
    print_result("Get Posts of User 1", res)
    posts = res.json()
    post_id = posts[0]["id"] if posts else None

    if post_id:
        # 7. Bob likes Alice's Post
        res = requests.post(f"{BASE_URL}/post/{post_id}/like", headers=headers2)
        print_result("Like Post (User 2)", res)

        # 8. Bob comments on Alice's Post
        res = requests.post(f"{BASE_URL}/post/{post_id}/comment", headers=headers2, json={"text": "Nice post Alice!"})
        print_result("Comment on Post (User 2)", res)

        # 9. Get feed for Bob (should be empty if not following, let's see)
        res = requests.get(f"{BASE_URL}/post/feed", headers=headers2)
        print_result("Get Feed (User 2)", res)

        # 10. Follow Request: Bob follows Alice
        res = requests.post(f"{BASE_URL}/follow/alice", headers=headers2)
        print_result("Follow Request (User 2 -> User 1)", res)
        # Note: FollowController expects `@PostMapping("/{receiver_username}")` but it might be differently mapped in actual code.
        # Actually FollowController is mapped to `/follow` probably? Wait, FollowController has no class level mapping.
        
    # Cleanup dummy file
    if os.path.exists("dummy.jpg"):
        os.remove("dummy.jpg")

if __name__ == "__main__":
    try:
        run_tests()
    except requests.exceptions.ConnectionError:
        print(f"{Colors.RED}Connection refused. Make sure the Spring Boot server is running!{Colors.RESET}")
