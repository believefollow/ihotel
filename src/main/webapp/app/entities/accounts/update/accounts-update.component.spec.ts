jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AccountsService } from '../service/accounts.service';
import { IAccounts, Accounts } from '../accounts.model';

import { AccountsUpdateComponent } from './accounts-update.component';

describe('Component Tests', () => {
  describe('Accounts Management Update Component', () => {
    let comp: AccountsUpdateComponent;
    let fixture: ComponentFixture<AccountsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let accountsService: AccountsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AccountsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AccountsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccountsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      accountsService = TestBed.inject(AccountsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const accounts: IAccounts = { id: 456 };

        activatedRoute.data = of({ accounts });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(accounts));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accounts = { id: 123 };
        spyOn(accountsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accounts });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accounts }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(accountsService.update).toHaveBeenCalledWith(accounts);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accounts = new Accounts();
        spyOn(accountsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accounts });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accounts }));
        saveSubject.complete();

        // THEN
        expect(accountsService.create).toHaveBeenCalledWith(accounts);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accounts = { id: 123 };
        spyOn(accountsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accounts });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(accountsService.update).toHaveBeenCalledWith(accounts);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
