jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CzCzl2Service } from '../service/cz-czl-2.service';
import { ICzCzl2, CzCzl2 } from '../cz-czl-2.model';

import { CzCzl2UpdateComponent } from './cz-czl-2-update.component';

describe('Component Tests', () => {
  describe('CzCzl2 Management Update Component', () => {
    let comp: CzCzl2UpdateComponent;
    let fixture: ComponentFixture<CzCzl2UpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let czCzl2Service: CzCzl2Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CzCzl2UpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CzCzl2UpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CzCzl2UpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      czCzl2Service = TestBed.inject(CzCzl2Service);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const czCzl2: ICzCzl2 = { id: 456 };

        activatedRoute.data = of({ czCzl2 });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(czCzl2));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czCzl2 = { id: 123 };
        spyOn(czCzl2Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czCzl2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: czCzl2 }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(czCzl2Service.update).toHaveBeenCalledWith(czCzl2);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czCzl2 = new CzCzl2();
        spyOn(czCzl2Service, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czCzl2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: czCzl2 }));
        saveSubject.complete();

        // THEN
        expect(czCzl2Service.create).toHaveBeenCalledWith(czCzl2);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const czCzl2 = { id: 123 };
        spyOn(czCzl2Service, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ czCzl2 });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(czCzl2Service.update).toHaveBeenCalledWith(czCzl2);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
