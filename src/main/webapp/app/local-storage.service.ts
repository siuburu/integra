import { Injectable } from '@angular/core';

interface StorageItem {
  key: string;
  values: string[] | string;
}

@Injectable({
  providedIn: 'root',
})
export class LocalStorageService {
  constructor() {}

  adicionarAoHistorico(item: any) {
    let historico: any[] = JSON.parse(localStorage.getItem('historico') || '[]');
    historico.unshift(item);
    historico = historico.slice(0, 4);
    localStorage.setItem('historico', JSON.stringify(historico));
  }

  recuperarUltimosItens(): any[] {
    const historico: any[] = JSON.parse(localStorage.getItem('historico') || '[]');
    return historico;
  }

  // setItem(key: string, values: string | string[]): void{
  //   const storageItem: StorageItem = {key, values};
  //   localStorage.setItem(key, JSON.stringify(storageItem));
  // }
  // getItem(key: string): string[] | string | null{
  //   const storageItemString = localStorage.getItem(key);
  //   if (storageItemString){
  //     const storageItem: StorageItem = JSON.parse(storageItemString);
  //     return storageItem.values;
  //   }
  //   return null;
  // }
  // removeItem(key: string): void{
  //   localStorage.removeItem(key);
  // }
  // clear(): void{
  //   localStorage.clear();
  // }
}
