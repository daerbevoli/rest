package be.uantwerpen.fti.ei.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.*;

/**
 * The BankService class with which the client can interact.
 * It uses a concurrentHashMap to store key value pairs where the key is the hashnumber of the account and the value is the account object.
 * It has GET methods to get the object properties, PUT methods to deposit and withdraw money and a POST method to create an account.
 * The creation of the account takes as request body as JSON file with key value pairs and
 * returns a unique hashcode associated with the account.
 * With this hashcode, the client can take actions on the associated account.

 * Below I will explain all the annotations.
 * @RestController indicates the controller in the application. It is reponsible for handling HTTP requests. 
 * @RequestMapping is used to map incoming requests to specifc handlers. This application can be reached at the handle '/bank'
 * @PostMapping, @GetMapping etc. are specific handles methods corresponding to CRUD operations.
 * @PathVariable is a handle method to bind a parameter to a URI template variable. 
 * 
*/


@RestController
@RequestMapping("/bank")
public class BankService {

    // This map is used to save bankaccount objects with the key, the hashcode of the account object and the value the object itself.
    // ConcurrentHashMap is a thread safe inmplementation of the Map interface. 
    // It allows multiple threads to read and modify the map concurrently without causing data corruption or inconsistency. 
    // The use here is justified because the assigment demanded that two clients could use a joint account concurrently.
    private final ConcurrentHashMap<Integer, BankAccount> map = new ConcurrentHashMap<>(1);

    // Create bank account and add it to the map. 
    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody BankAccount account){
        if (map.containsKey(account.getHash())) {
            return ResponseEntity.badRequest().body("Account with account number " + account.getAccountNumber() + " already exists");
        } else {
            map.putIfAbsent(account.getHash(), new BankAccount(account.getName(), account.getBalance(), account.getAccountNumber()));
            return ResponseEntity.ok("Account created \nThis is your hashcode: " + account.getHash());
        }
    }


    // Get method to test remote nodes.
    @GetMapping("/get")
    public ResponseEntity<String> get(){
        return ResponseEntity.ok("It works");
    }


    // The hash code has to be provided in the request URI 
    
    @GetMapping("/balance/{hash}")
    public double getBalance(@PathVariable int hash){
        return map.get(hash).getBalance();
    }


    @GetMapping("/name/{hash}")
    public String getName(@PathVariable int hash){
        return map.get(hash).getName();
    }

    @GetMapping("/accountNumber/{hash}")
    public String getAccountNumber(@PathVariable int hash){
        return map.get(hash).getAccountNumber();
    }

    // ResponseEntity is used to send a reponse.

    // Deposit of negative amount is not possible.
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DWdata data) {
        if (data.getAmount() < 0) {
            return ResponseEntity.ok("Amount to deposit cannot be negative\n" + HttpStatus.NOT_ACCEPTABLE);
        } else {
            map.get(data.getHash()).deposit(data.getAmount());
            return ResponseEntity.ok(data.getAmount() + " succesfully deposited\n" +
                    "new balance: " + map.get(data.getHash()).getBalance() + "\n" + HttpStatus.OK);
        }
    }

    // Withdraw of money is not possible if withdraw amount is greater than balance.
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody DWdata data){
        if (map.get(data.getHash()).getBalance() < data.getAmount()){
            return ResponseEntity.ok("Balance insufficient\n" + HttpStatus.NOT_ACCEPTABLE);
        } else {
            map.get(data.getHash()).withdraw(data.getAmount());
            return ResponseEntity.ok(data.getAmount() + " succesfully withdrawn\n" +
                    "remaining balance: " + map.get(data.getHash()).getBalance() + "\n" + HttpStatus.OK);
        }
    }

    @DeleteMapping("/deleteBalance")
    public ResponseEntity<String> delete(@RequestBody int hash){
        map.get(hash).setBalance(0);
        return ResponseEntity.ok("Balance deleted\n" + HttpStatus.OK);
    }
        
}
