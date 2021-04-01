jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DEmpnService } from '../service/d-empn.service';
import { IDEmpn, DEmpn } from '../d-empn.model';

import { DEmpnUpdateComponent } from './d-empn-update.component';

describe('Component Tests', () => {
  describe('DEmpn Management Update Component', () => {
    let comp: DEmpnUpdateComponent;
    let fixture: ComponentFixture<DEmpnUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dEmpnService: DEmpnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DEmpnUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DEmpnUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DEmpnUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dEmpnService = TestBed.inject(DEmpnService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dEmpn: IDEmpn = { id: 456 };

        activatedRoute.data = of({ dEmpn });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dEmpn));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dEmpn = { id: 123 };
        spyOn(dEmpnService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dEmpn });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dEmpn }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dEmpnService.update).toHaveBeenCalledWith(dEmpn);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dEmpn = new DEmpn();
        spyOn(dEmpnService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dEmpn });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dEmpn }));
        saveSubject.complete();

        // THEN
        expect(dEmpnService.create).toHaveBeenCalledWith(dEmpn);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dEmpn = { id: 123 };
        spyOn(dEmpnService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dEmpn });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dEmpnService.update).toHaveBeenCalledWith(dEmpn);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
