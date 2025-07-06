FROM bitnami/minideb:latest
RUN bash -c "echo \"$(< /dev/urandom tr -cd "[:print:]" | head -c 32; echo)\" > /tmp/auth"
RUN <<EOF cat > /remove
#!/usr/bin/env bash
test -f /tmp/auth && rm -f /tmp/auth
EOF
RUN chmod +x /remove; \
    bash /remove
CMD ["/usr/bin/echo", "done"]
