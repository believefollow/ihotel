jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CzCzl3Service } from '../service/cz-czl-3.service';
import { ICzCzl3, CzCzl3 } from '../cz-czl-3.model';

import { CzCzl3UpdateComponent } from './cz-czl-3-update.component';

describe('Component Tests', () => {
  describe('CzCzl3 Management Update Component', () => {
    let comp: CzCzl3UpdateComponent;
    let fixture: ComponentFixture<CzCzl3UpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let czCzl3Service: CzCzl3Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CzCzl3UpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CzCzl3UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CzCzl3UpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      czCzl3Service = TestBed.inject(CzCzl3Service);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const czCzl3: ICzCzl3 = { id: 456 };

        activatedRoute.data = of({ czCzl3 });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(czCzl3));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czCzl3 = { id: 123 };
        spyOn(czCzl3Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czCzl3 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: czCzl3 }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(czCzl3Service.update).toHaveBeenCalledWith(czCzl3);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czCzl3 = new CzCzl3();
        spyOn(czCzl3Service, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czCzl3 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: czCzl3 }));
        saveSubject.complete();

        // THEN
        expect(czCzl3Service.create).toHaveBeenCalledWith(czCzl3);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czCzl3 = { id: 123 };
        spyOn(czCzl3Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czCzl3 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(czCzl3Service.update).toHaveBeenCalledWith(czCzl3);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
