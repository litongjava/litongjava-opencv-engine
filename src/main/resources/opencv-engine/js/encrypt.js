function encrypt(text) {
  let key = crypto.getRandomValues(new Uint8Array(16));
  let textBytes = aesjs.utils.utf8.toBytes(text);
  let aesCtr = new aesjs.ModeOfOperation.ctr(key);
  let encryptedBytes = aesCtr.encrypt(textBytes);
  return { text: aesjs.utils.hex.fromBytes(encryptedBytes), key: aesjs.utils.hex.fromBytes(key) };
}