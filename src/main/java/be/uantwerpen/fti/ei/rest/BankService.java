package be.uantwerpen.fti.ei.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.*;

/**
 * The BankService class with which the client can interact using REST.
 * It uses a concurrentHashMap to store key value pairs where the key is the hashnumber of the account
 * and the value is the account object.
 * it has GET methods to het the object properties, PUT methods to deposit and withdraw money
 * and a POST method to create an account.
 * The creation of the account return a unique hashcode associated with the account.
 * With this hashcode, the client can take actions on the associated account.
 */
@RestController
@RequestMapping("/api/bank")
public class BankService {


    private final ConcurrentHashMap<Integer, BankAccount> map = new ConcurrentHashMap<>(1);

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody BankAccount account){
        map.putIfAbsent(account.getHash(), new BankAccount(account.getName(), account.getBalance(), account.getAccountNumber()));
        return ResponseEntity.ok("Account created \nThis is your hashcode: " + account.getHash());
    }


    @GetMapping("/balance")
    public double getBalance(@RequestBody int hash){
        return map.get(hash).getBalance();
    }


    @GetMapping("/name")
    public String getName(@RequestBody int hash){
        return map.get(hash).getName();
    }

    @GetMapping("/accountNumber")
    public String getAccountNumber(@RequestBody int hash){
        return map.get(hash).getAccountNumber();
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DWdata data){
        map.get(data.getHash()).deposit(data.getAmount());
        return ResponseEntity.ok(data.getAmount() + " succesfully deposited\n" +
                "new balance: " + map.get(data.getHash()).getBalance() + "\n" + HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody DWdata data){
        if (map.get(data.getHash()).getBalance() < data.getAmount()){
            return ResponseEntity.ok("Balance insufficient\n" + HttpStatus.NOT_ACCEPTABLE);
        } else {
            map.get(data.getHash()).withdraw(data.getAmount());
            return ResponseEntity.ok(data.getAmount() + " succesfully withdrawn\n" +
                    "remaining balance: " + map.get(1).getBalance() + "\n" + HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteBalance")
    public ResponseEntity<String> delete(@RequestBody int hash){
        map.get(hash).setBalance(0);
        return ResponseEntity.ok("Balance deleted\n" + HttpStatus.OK);
    }



}
