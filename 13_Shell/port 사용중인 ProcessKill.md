#### 포트가 어디서 쓰고 있는지 상세히 확인

```bash
netstat -napb tcp | more
```

#### 현재 사용중인 포트 및 PID 확인

```bash
netstat -a -o
```

#### 특정 포트 및 PID 확인

```bash
netstat -ano | find "8080"
```

#### PID로 특정 포트 죽이기

```bash
taskkill /f /pid (pid번호)
```
